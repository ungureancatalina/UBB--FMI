package com.example.android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val author: String,
    val publishDate: String,
    val available: Boolean,
    val photo: String?,
    val lat: Double?,
    val lng: Double?
)
