package com.example.android.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

fun BookEntity.toBook(): Book {
    val date: Date = try {
        dateFormat.parse(publishDate) ?: Date()
    } catch (e: Exception) {
        Date()
    }
    return Book(
        id = id,
        title = title,
        author = author,
        publishDate = date,
        available = available,
        photo = photo,
        lat = lat,
        lng = lng
    )
}

fun Book.toEntity(): BookEntity {
    val dateString = dateFormat.format(publishDate)
    return BookEntity(
        id = id,
        title = title,
        author = author,
        publishDate = dateString,
        available = available,
        photo = photo,
        lat = lat,
        lng = lng
    )
}
