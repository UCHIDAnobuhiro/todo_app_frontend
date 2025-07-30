package com.example.todoapp.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

object ApiClient {
    private const val BASE_URL ="http://10.0.2.2:8080"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val todoApi: TodoApi = retrofit.create(TodoApi::class.java)
}