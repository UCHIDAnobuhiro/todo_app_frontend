package com.example.todoapp.data.repository

import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.network.ApiClient
import kotlinx.coroutines.delay
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
            fetchTodos() // 追加後に再取得
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}