package com.example.android.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PendingChangeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(change: PendingChangeEntity)

    @Query("SELECT * FROM pending_changes ORDER BY id ASC")
    suspend fun getAll(): List<PendingChangeEntity>

    @Query("DELETE FROM pending_changes WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM pending_changes")
    suspend fun clear()
}
