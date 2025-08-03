package com.example.todoapp.data.network

import com.example.todoapp.data.model.Todo
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApi {
    @Serializable
    data class MessageResponse(val message: String)

    @GET("/todos")
    suspend fun getTodos(): List<Todo>

    @POST("/todos")
    suspend fun addTodo(@Body todo: Todo): MessageResponse

    @PUT("/todos/{id}")
    suspend fun updateTodo(@Path("id") id: Int, @Body todo: Todo): MessageResponse

    @DELETE("/todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int): MessageResponse
}