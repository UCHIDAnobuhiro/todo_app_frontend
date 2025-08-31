package com.example.todoapp.data.network

import com.example.todoapp.data.model.LoginRequest
import com.example.todoapp.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

}