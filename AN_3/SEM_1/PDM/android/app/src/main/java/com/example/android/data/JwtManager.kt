package com.example.android.data

import android.content.Context
import android.util.Log

object JwtManager {
    private const val PREFS_NAME = "auth_prefs"
    private const val KEY_TOKEN = "jwt_token"
    private const val TAG = "JwtManager"

    fun saveToken(context: Context, token: String) {
        Log.d(TAG, "saveToken: $token")
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val token = prefs.getString(KEY_TOKEN, null)
        Log.d(TAG, "getToken: $token")
        return token
    }

    fun clearToken(context: Context) {
        Log.d(TAG, "clearToken")
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_TOKEN).apply()
    }
}
