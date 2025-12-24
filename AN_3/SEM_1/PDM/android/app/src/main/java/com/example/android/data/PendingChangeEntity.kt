package com.example.android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_changes")
data class PendingChangeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val operation: String,
    val bookId: Int?,
    val payloadJson: String?
)
