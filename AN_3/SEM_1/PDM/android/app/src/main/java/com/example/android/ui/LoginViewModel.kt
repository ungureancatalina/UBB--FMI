package com.example.android.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.data.BookRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val tag = "LoginViewModel"

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoggedIn by mutableStateOf(repository.isLoggedIn())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onUsernameChange(newValue: String) {
        Log.d(tag, "onUsernameChange: $newValue")
        username = newValue
    }

    fun onPasswordChange(newValue: String) {
        Log.d(tag, "onPasswordChange")
        password = newValue
    }

    fun login() {
        Log.d(tag, "login() start")
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val success = repository.login(username, password)
            isLoggedIn = success
            if (!success) {
                errorMessage = "Invalid credentials"
                Log.d(tag, "login() failed")
            } else {
                Log.d(tag, "login() success")
            }
            isLoading = false
        }
    }

    fun logout() {
        Log.d(tag, "logout()")
        repository.clearToken()
        isLoggedIn = false
        username = ""
        password = ""
    }
}
