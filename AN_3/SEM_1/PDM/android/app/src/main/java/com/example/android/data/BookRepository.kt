package com.example.android.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BookRepository(private val context: Context) {

    private val apiService: BookApiService =
        Api.retrofit.create(BookApiService::class.java)

    private val tag = "BookRepository"
    private val db = AppDatabase.getInstance(context)
    private val gson = Gson()

    fun isLoggedIn(): Boolean {
        val loggedIn = JwtManager.getToken(context) != null
        Log.d(tag, "isLoggedIn = $loggedIn")
        return loggedIn
    }

    suspend fun login(username: String, password: String): Boolean {
        Log.d(tag, "login username=$username")
        return try {
            val response = apiService.login(LoginRequest(username, password))
            Log.d(tag, "login success, token=${response.token}")
            JwtManager.saveToken(context, response.token)
            true
        } catch (e: Exception) {
            Log.e(tag, "login failed", e)
            false
        }
    }

    private fun requireAuthHeader(): String {
        val token = JwtManager.getToken(context)
            ?: throw IllegalStateException("User not authenticated")
        return "Bearer $token"
    }

    suspend fun getBooks(forceRefresh: Boolean = false): List<Book> {
        Log.d(tag, "getBooks(forceRefresh=$forceRefresh)")
        return try {
            if (forceRefresh) {
                fetchFromServerAndCache()
            } else {
                val local = db.bookDao().getAll().map { it.toBook() }
                if (local.isNotEmpty()) {
                    Log.d(tag, "getBooks -> from local DB (${local.size})")
                    local
                } else {
                    fetchFromServerAndCache()
                }
            }
        } catch (e: Exception) {
            Log.e(tag, "getBooks() error, fallback to local", e)
            db.bookDao().getAll().map { it.toBook() }
        }
    }

    private suspend fun fetchFromServerAndCache(): List<Book> {
        val result = apiService.getBooks(requireAuthHeader())
        Log.d(tag, "fetchFromServerAndCache -> ${result.size} items")
        val entities = result.map { it.toEntity() }
        db.bookDao().deleteAll()
        db.bookDao().insertAll(entities)
        return result
    }

    suspend fun addBook(book: Book, offlineAllowed: Boolean = true) {
        Log.d(tag, "addBook: $book")
        try {
            val created = apiService.addBook(requireAuthHeader(), book.copy(id = 0))
            db.bookDao().deleteById(book.id)
             db.bookDao().insert(created.toEntity())

        } catch (e: Exception) {
            Log.e(tag, "addBook failed, offlineAllowed=$offlineAllowed", e)
            if (offlineAllowed) {
                val tempId = (System.currentTimeMillis() / 1000).toInt() * -1
                val localBook = book.copy(id = tempId)
                db.bookDao().insert(localBook.toEntity())

                val payloadJson = gson.toJson(localBook)
                db.pendingChangeDao().insert(
                    PendingChangeEntity(
                        operation = "CREATE",
                        bookId = null,
                        payloadJson = payloadJson
                    )
                )
            } else {
                throw e
            }
        }
    }

    suspend fun updateBook(book: Book, offlineAllowed: Boolean = true) {
        Log.d(tag, "updateBook: $book")
        try {
            val updated = apiService.updateBook(requireAuthHeader(), book.id, book)
            db.bookDao().insert(updated.toEntity())
        } catch (e: Exception) {
            Log.e(tag, "updateBook failed, offlineAllowed=$offlineAllowed", e)
            if (offlineAllowed) {
                db.bookDao().insert(book.toEntity())
                val payloadJson = gson.toJson(book)
                db.pendingChangeDao().insert(
                    PendingChangeEntity(
                        operation = "UPDATE",
                        bookId = book.id,
                        payloadJson = payloadJson
                    )
                )
            } else {
                throw e
            }
        }
    }

    suspend fun deleteBook(id: Int, offlineAllowed: Boolean = true) {
        Log.d(tag, "deleteBook id=$id")
        try {
            apiService.deleteBook(requireAuthHeader(), id)
            db.bookDao().delete(id)
        } catch (e: Exception) {
            Log.e(tag, "deleteBook failed, offlineAllowed=$offlineAllowed", e)
            if (offlineAllowed) {
                db.bookDao().delete(id)
                db.pendingChangeDao().insert(
                    PendingChangeEntity(
                        operation = "DELETE",
                        bookId = id,
                        payloadJson = null
                    )
                )
            } else {
                throw e
            }
        }
    }

    fun clearToken() {
        Log.d(tag, "clearToken()")
        JwtManager.clearToken(context)
    }

    suspend fun syncPendingChanges(): Boolean {
        var changed = false
        val pendingList = db.pendingChangeDao().getAll()

        for (change in pendingList) {
            try {
                when (change.operation) {
                    "CREATE" -> {
                        val book: Book = gson.fromJson(change.payloadJson, Book::class.java)
                        apiService.addBook(requireAuthHeader(), book.copy(id = 0))
                        changed = true
                    }
                    "UPDATE" -> {
                        val book: Book = gson.fromJson(change.payloadJson, Book::class.java)
                        apiService.updateBook(requireAuthHeader(), book.id, book)
                        changed = true
                    }
                    "DELETE" -> {
                        apiService.deleteBook(requireAuthHeader(), change.bookId!!)
                        changed = true
                    }
                }
                db.pendingChangeDao().delete(change.id)
            } catch (_: Exception) {}
        }
        return changed
    }
}
