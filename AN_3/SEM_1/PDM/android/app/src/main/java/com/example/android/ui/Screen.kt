package com.example.android.ui

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object BookList : Screen("book_list")
    object EditBook : Screen("edit_book/{bookId}") {
        fun createRoute(bookId: Int) = "edit_book/$bookId"
    }
}
