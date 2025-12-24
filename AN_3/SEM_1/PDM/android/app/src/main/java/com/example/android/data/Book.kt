package com.example.android.data

import java.io.Serializable
import java.util.Date

data class Book(
    val id: Int,
    var title: String,
    var author: String,
    var publishDate: Date,
    var available: Boolean,
    var photo: String? = null,
    var lat: Double? = null,
    var lng: Double? = null
) : Serializable
