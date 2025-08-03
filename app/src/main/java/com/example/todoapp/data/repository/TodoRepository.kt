package com.example.todoapp.data.repository

import android.util.Log
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TodoRepository {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    private val api = ApiClient.todoApi

    suspend fun fetchTodos() {
        try {
            val result = api.getTodos()
            _todos.value = result
        } catch (e: Exception) {
            // エラーハンドリング（ログやUI通知）
            e.printStackTrace()
        }
    }

    suspend fun addTodo(title: String) {
        try {
            val newTodo = Todo(title = title, completed = false)
            api.addTodo(newTodo)
            _todos.value = api.getTodos()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateTodo(todo: Todo) {
        try {
            api.updateTodo(todo.id, todo)
            val updatedTodos = api.getTodos()
            _todos.value = updatedTodos
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("error", "error: $e")
        }
    }

    suspend fun deleteTodo(todo: Todo) {
        try {
            api.deleteTodo(todo.id)
            val updatedTodos = api.getTodos()
            _todos.value = updatedTodos
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}