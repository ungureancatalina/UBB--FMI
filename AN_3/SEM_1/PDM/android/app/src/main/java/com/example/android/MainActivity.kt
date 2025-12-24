package com.example.android

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.android.data.BookRepository
import com.example.android.ui.*
import com.example.android.utils.NetworkMonitor
import com.example.android.utils.ShakeDetector
import com.example.android.utils.SyncWorker
import kotlinx.coroutines.flow.collectLatest
import java.util.concurrent.TimeUnit
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.SensorEventListener
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.android.utils.SocketManager
import com.example.android.data.JwtManager


class MainActivity : ComponentActivity() {

    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var shakeDetector: ShakeDetector? = null
    private var proximitySensor: Sensor? = null
    private var proximityListener: SensorEventListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = BookRepository(this)

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "books_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        proximitySensor = sensorManager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        Log.d("SENSOR_CHECK", "Proximity sensor = $proximitySensor")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        setContent {
            val navController = rememberNavController()
            val factory = remember {
                AppViewModelFactory(
                    repository,
                    applicationContext
                )
            }

            val loginViewModel: LoginViewModel = viewModel(factory = factory)
            val bookViewModel: BookViewModel = viewModel(factory = factory)

            proximityListener = object : SensorEventListener {
                override fun onSensorChanged(event: android.hardware.SensorEvent) {
                    val distance = event.values[0]

                    if (distance < (proximitySensor?.maximumRange ?: 0f)) {
                        Log.d("Proximity", "Near detected")
                        bookViewModel.markFirstAsUnavailable()
                    } else {
                        Log.d("Proximity", "Far detected")
                        bookViewModel.markFirstAsAvailable()
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            val startDestination =
                if (loginViewModel.isLoggedIn) Screen.BookList.route else Screen.Login.route

            val networkMonitor = remember { NetworkMonitor(this) }
            LaunchedEffect(Unit) {
                networkMonitor.networkStatusFlow().collectLatest { online ->
                    Log.d("MainActivity", "Network online=$online")
                    bookViewModel.updateOnlineStatus(online)
                    if (online) {
                        val changed = repository.syncPendingChanges()
                        if (changed) {
                            bookViewModel.refresh()
                        }
                    }

                }
            }

            shakeDetector = ShakeDetector {
                Log.d("MainActivity", "Shake detected, mark first as available")
                bookViewModel.markFirstAsAvailable()
            }

            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable(Screen.Login.route) {
                    LoginScreen(viewModel = loginViewModel)
                    LaunchedEffect(loginViewModel.isLoggedIn) {
                        if (loginViewModel.isLoggedIn) {

                            val token = JwtManager.getToken(this@MainActivity)
                            if (token != null) {
                                bookViewModel.startSocket(token)
                            }

                            bookViewModel.refresh()

                            navController.navigate(Screen.BookList.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    }
                }

                composable(Screen.BookList.route) {
                    LaunchedEffect(Unit) {
                        bookViewModel.refresh()
                    }
                    BookListScreen(
                        books = bookViewModel.books,
                        isLoading = bookViewModel.isLoading,
                        isOnline = bookViewModel.isOnline,
                        search = bookViewModel.search,
                        filter = bookViewModel.filter,
                        onSearchChange = { bookViewModel.onSearchChange(it) },
                        onFilterChange = { bookViewModel.onFilterChange(it) },
                        onLoadMore = { bookViewModel.loadMore() },
                        onRefresh = { bookViewModel.refresh() },
                        onAddBook = {
                            navController.navigate(Screen.EditBook.createRoute(-1))
                        },
                        onBookClick = { book ->
                            navController.navigate(Screen.EditBook.createRoute(book.id))
                        },
                        onLogout = {
                            loginViewModel.logout()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.BookList.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Screen.EditBook.route) { backStackEntry ->
                    val bookIdArg = backStackEntry.arguments?.getString("bookId") ?: "-1"
                    val bookId = bookIdArg.toIntOrNull() ?: -1

                    val existingBook =
                        if (bookId != -1) bookViewModel.getBookById(bookId) else null

                    EditBookScreen(
                        existingBook = existingBook,
                        onSave = { title, author, dateString, available, photo, lat, lng ->
                            val id = existingBook?.id
                            bookViewModel.saveBook(id, title, author, dateString, available, photo, lat, lng)
                            navController.popBackStack()
                        },
                        onDelete = { id ->
                            bookViewModel.deleteBook(id)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let { sensor ->
            shakeDetector?.let { detector ->
                sensorManager?.registerListener(detector, sensor, SensorManager.SENSOR_DELAY_UI)
            }
        }
        proximitySensor?.let { sensor ->
            proximityListener?.let { listener ->
                sensorManager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        shakeDetector?.let {
            sensorManager?.unregisterListener(it)
        }
        proximityListener?.let {
            sensorManager?.unregisterListener(it)
        }
    }
}

class AppViewModelFactory(
    private val repository: BookRepository,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(repository) as T

            modelClass.isAssignableFrom(BookViewModel::class.java) ->
                BookViewModel(repository, context) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}
