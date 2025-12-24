package com.example.android.utils

import android.util.Log
import com.example.android.data.Book
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.text.SimpleDateFormat

object SocketManager {

    private const val TAG = "SocketManager"
    private var socket: Socket? = null

    fun connect(
        token: String,
        onBookCreated: (Book) -> Unit,
        onBookUpdated: (Book) -> Unit,
        onBookDeleted: (Int) -> Unit
    ) {
        if (socket?.connected() == true) return

        val opts = IO.Options().apply {
            auth = mapOf("token" to "Bearer $token")
        }

        socket = IO.socket("http://10.0.2.2:3000", opts)

        socket?.on(Socket.EVENT_CONNECT) {
            Log.d(TAG, "Socket connected")
        }

        socket?.on("book_created") {
            val book = jsonToBook(it[0] as JSONObject)
            onBookCreated(book)
        }

        socket?.on("book_updated") {
            val book = jsonToBook(it[0] as JSONObject)
            onBookUpdated(book)
        }

        socket?.on("book_deleted") {
            val id = (it[0] as JSONObject).getInt("id")
            onBookDeleted(id)
        }

        socket?.connect()
    }

    fun disconnect() {
        socket?.disconnect()
        socket = null
    }

    private fun jsonToBook(json: JSONObject): Book {
        val df = SimpleDateFormat("yyyy-MM-dd")
        return Book(
            id = json.getInt("id"),
            title = json.getString("title"),
            author = json.getString("author"),
            publishDate = df.parse(json.getString("publishDate"))!!,
            available = json.getBoolean("available"),
            photo = json.optString("photo", null),
            lat = json.optDouble("lat").takeIf { !it.isNaN() },
            lng = json.optDouble("lng").takeIf { !it.isNaN() }
        )
    }
}
