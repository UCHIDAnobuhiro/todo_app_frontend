package com.example.todoapp.data.network

import com.example.todoapp.data.model.Todo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoApi {
    @GET("/todos")
    suspend fun getTodos(): List<Todo>

    @POST("/todos")
    suspend fun addTodo(@Body todo: Todo): Unit
}