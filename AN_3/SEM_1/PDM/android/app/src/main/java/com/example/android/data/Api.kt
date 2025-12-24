package com.example.android.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Api {

    private const val url = "10.0.2.2:3000"
    private const val httpUrl = "http://$url/"

    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
        .create()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()


    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(httpUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}
