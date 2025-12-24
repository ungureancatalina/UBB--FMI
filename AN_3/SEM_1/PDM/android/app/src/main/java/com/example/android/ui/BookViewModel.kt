package com.example.android.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.data.Book
import com.example.android.data.BookRepository
import com.example.android.utils.NotificationHelper
import com.example.android.utils.SocketManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

enum class BookFilter { ALL, AVAILABLE, UNAVAILABLE }

class BookViewModel(
    private val repository: BookRepository,
    private val context: Context
) : ViewModel() {


    private val tag = "BookViewModel"

    var books by mutableStateOf<List<Book>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isOnline by mutableStateOf(true)
        private set

    var search by mutableStateOf("")
        private set

    var filter by mutableStateOf(BookFilter.ALL)
        private set

    var page by mutableStateOf(1)
        private set

    var hasMore by mutableStateOf(true)
        private set

    private val pageSize = 10

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun updateOnlineStatus(online: Boolean) {
        isOnline = online
    }


    fun onSearchChange(newValue: String) {
        search = newValue
        refresh()
    }

    fun onFilterChange(newFilter: BookFilter) {
        filter = newFilter
        refresh()
    }

    fun refresh() {
        page = 1
        loadBooks(forceRefresh = true)
    }

    fun loadMore() {
        if (!hasMore || isLoading) return
        page += 1
        loadBooks()
    }

    fun loadBooks(forceRefresh: Boolean = false) {
        Log.d(tag, "loadBooks(forceRefresh=$forceRefresh, page=$page)")
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val result = repository.getBooks(forceRefresh)
                val filtered = result.filter { book ->
                    val matchesFilter = when (filter) {
                        BookFilter.ALL -> true
                        BookFilter.AVAILABLE -> book.available
                        BookFilter.UNAVAILABLE -> !book.available
                    }
                    val matchesSearch =
                        search.isBlank() ||
                                book.title.contains(search, ignoreCase = true) ||
                                book.author.contains(search, ignoreCase = true)

                    matchesFilter && matchesSearch
                }

                val fromIndex = (page - 1) * pageSize
                val toIndex = (fromIndex + pageSize).coerceAtMost(filtered.size)
                hasMore = toIndex < filtered.size

                books = if (fromIndex < filtered.size) {
                    filtered.subList(0, toIndex)
                } else {
                    filtered
                }

                Log.d(tag, "loadBooks() -> total=${filtered.size}, shown=${books.size}")
            } catch (e: Exception) {
                Log.e(tag, "Error loading books", e)
                errorMessage = "Error loading books: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun getBookById(id: Int): Book? {
        Log.d(tag, "getBookById($id)")
        return books.firstOrNull { it.id == id }
    }

    fun saveBook(
        existingId: Int?,
        title: String,
        author: String,
        dateString: String,
        available: Boolean,
        photo: String?,
        lat: Double?,
        lng: Double?
    ) {
        Log.d(tag, "saveBook(existingId=$existingId, title=$title)")
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val publishDate: Date = try {
                    dateFormat.parse(dateString) ?: Date()
                } catch (e: Exception) {
                    Log.e(tag, "Date parse error", e)
                    Date()
                }

                if (existingId != null && existingId != -1) {
                    val updated = Book(
                        id = existingId,
                        title = title,
                        author = author,
                        publishDate = publishDate,
                        available = available,
                        photo = photo,
                        lat = lat,
                        lng = lng
                    )
                    repository.updateBook(updated)
                } else {
                    val newBook = Book(
                        id = 0,
                        title = title,
                        author = author,
                        publishDate = publishDate,
                        available = available,
                        photo = photo,
                        lat = lat,
                        lng = lng
                    )
                    repository.addBook(newBook)
                }
                refresh()
            } catch (e: Exception) {
                Log.e(tag, "Error saving book", e)
                errorMessage = "Error saving book: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun deleteBook(id: Int) {
        Log.d(tag, "deleteBook($id)")
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                repository.deleteBook(id)
                refresh()
            } catch (e: Exception) {
                Log.e(tag, "Error deleting book", e)
                errorMessage = "Error deleting book: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun formatDate(date: Date): String = dateFormat.format(date)

    fun markFirstAsAvailable() {
        viewModelScope.launch {
            val first = books.firstOrNull() ?: return@launch
            if (first.available) return@launch
            val updated = first.copy(available = true)
            repository.updateBook(updated)
            refresh()
        }
    }

    fun markFirstAsUnavailable() {
        viewModelScope.launch {
            val first = books.firstOrNull() ?: return@launch
            if (!first.available) return@launch
            val updated = first.copy(available = false)
            repository.updateBook(updated)
            refresh()
        }
    }

    fun startSocket(token: String) {
        SocketManager.connect(
            token = token,
            onBookCreated = { book ->
                books = listOf(book) + books

                NotificationHelper.showNotification(
                    context,
                    "Carte nouă",
                    "A fost adăugată: ${book.title}"
                )
            },
            onBookUpdated = { updated ->
                books = books.map {
                    if (it.id == updated.id) updated else it
                }
            },
            onBookDeleted = { id ->
                books = books.filterNot { it.id == id }
            }
        )
    }
}
