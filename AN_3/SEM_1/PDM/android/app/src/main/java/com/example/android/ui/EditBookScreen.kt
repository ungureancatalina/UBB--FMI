package com.example.android.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.android.data.Book
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch


@Composable
fun EditBookScreen(
    existingBook: Book?,
    onSave: (String, String, String, Boolean, String?, Double?, Double?) -> Unit,
    onDelete: (Int) -> Unit,
) {

    val tag = "EditBookScreen"
    Log.d(tag, "Open screen for book id=${existingBook?.id}")

    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf(existingBook?.title ?: "") }
    var author by remember { mutableStateOf(existingBook?.author ?: "") }

    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    var dateString by remember {
        mutableStateOf(
            existingBook?.let { dateFormat.format(it.publishDate) } ?: dateFormat.format(Date())
        )
    }

    var available by remember { mutableStateOf(existingBook?.available ?: false) }

    var photoPath by remember { mutableStateOf(existingBook?.photo) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            val file = File(context.filesDir, "book_${System.currentTimeMillis()}.jpg")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }
            photoPath = file.absolutePath
        }
    }

    val currentBitmap: Bitmap? = remember(photoPath) {
        photoPath?.let { path ->
            val file = File(path)
            if (file.exists()) BitmapFactory.decodeFile(path) else null
        }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val input = context.contentResolver.openInputStream(uri)
            val file = File(context.filesDir, "book_${System.currentTimeMillis()}.jpg")
            val output = FileOutputStream(file)

            input?.copyTo(output)
            input?.close()
            output.close()

            photoPath = file.absolutePath
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = if (existingBook != null) "Edit Book" else "Add Book",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = dateString,
                onValueChange = { dateString = it },
                label = { Text("Publish date (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Checkbox(
                    checked = available,
                    onCheckedChange = { available = it }
                )
                Text(text = "Available")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { cameraLauncher.launch(null) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Take photo")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    pickImageLauncher.launch("image/*")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Photo")
            }


            Spacer(modifier = Modifier.height(8.dp))

            if (currentBitmap != null) {
                Image(
                    bitmap = currentBitmap.asImageBitmap(),
                    contentDescription = "Book photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (title.isBlank() || author.isBlank() || dateString.isBlank()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Please fill all fields")
                        }
                    } else {
                        onSave(title, author, dateString, available, photoPath, null, null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }

            if (existingBook != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("Confirm Delete") },
                        text = { Text("Are you sure you want to delete this book?") },
                        confirmButton = {
                            TextButton(onClick = {
                                onDelete(existingBook.id)
                                showDeleteDialog = false
                            }) {
                                Text("Yes", color = MaterialTheme.colorScheme.error)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDeleteDialog = false
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
