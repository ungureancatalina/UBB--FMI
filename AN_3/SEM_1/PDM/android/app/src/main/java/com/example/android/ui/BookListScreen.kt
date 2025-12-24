package com.example.android.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.android.data.Book
import java.io.File
import android.graphics.BitmapFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    books: List<Book>,
    isLoading: Boolean,
    isOnline: Boolean,
    search: String,
    filter: BookFilter,
    onSearchChange: (String) -> Unit,
    onFilterChange: (BookFilter) -> Unit,
    onLoadMore: () -> Unit,
    onRefresh: () -> Unit,
    onAddBook: () -> Unit,
    onBookClick: (Book) -> Unit,
    onLogout: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "addButtonPulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scaleAnim"
    )
    var selectedBookForDialog by remember { mutableStateOf<Book?>(null) }
    var showInfoDialog by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Library") },
                actions = {
                    TextButton(onClick = onRefresh) { Text("Refresh") }
                    TextButton(onClick = onLogout) { Text("Logout") }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(12.dp)
                .fillMaxSize()
        ) {
            Text(
                text = if (isOnline) "🟢 Online" else "🟠 Offline",
                color = if (isOnline) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.tertiary
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = onAddBook,
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(scale)
            ) {
                Text("Add Book")
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = search,
                onValueChange = onSearchChange,
                label = { Text("Search by title/author") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterChip(
                    selected = filter == BookFilter.ALL,
                    onClick = { onFilterChange(BookFilter.ALL) },
                    label = { Text("All") }
                )
                FilterChip(
                    selected = filter == BookFilter.AVAILABLE,
                    onClick = { onFilterChange(BookFilter.AVAILABLE) },
                    label = { Text("Available") }
                )
                FilterChip(
                    selected = filter == BookFilter.UNAVAILABLE,
                    onClick = { onFilterChange(BookFilter.UNAVAILABLE) },
                    label = { Text("Unavailable") }
                )
            }

            Spacer(Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(books) { book ->
                    BookListItem(
                        book = book,
                        onClick = { selectedBookForDialog = book
                                    showInfoDialog = true
                        },
                    )
                }

                item {
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        LaunchedEffect(Unit) {
                            onLoadMore()
                        }
                    }
                }
            }
            if (showInfoDialog && selectedBookForDialog != null) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = showInfoDialog,
                    enter = androidx.compose.animation.scaleIn() + androidx.compose.animation.fadeIn(),
                    exit = androidx.compose.animation.scaleOut() + androidx.compose.animation.fadeOut()
                ) {
                    AlertDialog(
                        onDismissRequest = { showInfoDialog = false },
                        title = { Text("Book Selected") },
                        text = { Text(selectedBookForDialog!!.title) },
                        confirmButton = {
                            TextButton(onClick = {
                                onBookClick(selectedBookForDialog!!)
                                showInfoDialog = false
                            }) {
                                Text("Edit")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showInfoDialog = false
                            }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun BookListItem(
    book: Book,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
            .animateContentSize()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            Text("Author: ${book.author}")
            Text("Available: ${book.available}")


            if (book.id < 0) {
                Text("Added offline (not synced yet)", color = MaterialTheme.colorScheme.error)
            }

            if (!book.photo.isNullOrBlank()) {
                val file = File(book.photo!!)
                if (file.exists()) {
                    val bitmap = remember(book.photo) {
                        BitmapFactory.decodeFile(book.photo)
                    }
                    bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Book photo",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                    }
                }
            }
        }
    }
}
