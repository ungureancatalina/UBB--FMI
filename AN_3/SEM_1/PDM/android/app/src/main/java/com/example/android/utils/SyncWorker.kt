package com.example.android.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.data.BookRepository

class SyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val tag = "SyncWorker"

    override suspend fun doWork(): Result {
        return try {
            Log.d(tag, "doWork()")
            val repo = BookRepository(applicationContext)
            repo.syncPendingChanges()
            NotificationHelper.showNotification(
                applicationContext,
                "Sync complete",
                "Pending changes have been sent to server"
            )
            Result.success()
        } catch (e: Exception) {
            Log.e(tag, "sync failed", e)
            Result.retry()
        }
    }
}
