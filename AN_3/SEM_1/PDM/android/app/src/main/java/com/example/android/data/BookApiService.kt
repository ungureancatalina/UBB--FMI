package com.example.android.data

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String
)

interface BookApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("books")
    suspend fun getBooks(
        @Header("Authorization") authHeader: String
    ): List<Book>

    @POST("books")
    suspend fun addBook(
        @Header("Authorization") authHeader: String,
        @Body book: Book
    ): Book

    @PUT("books/{id}")
    suspend fun updateBook(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int,
        @Body book: Book
    ): Book

    @DELETE("books/{id}")
    suspend fun deleteBook(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int
    )
}
