package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    private val repository = TodoRepository()

    val todos: StateFlow<List<Todo>> = repository.todos

    init {
        viewModelScope.launch {
            Log.d("ViewModel", "fetchTodos called")
            repository.fetchTodos()
        }
    }

    fun addTodo(title: String) {
        viewModelScope.launch {
            repository.addTodo(title)
        }
    }

    fun toggleCompleted(todo: Todo) {
        viewModelScope.launch {
            val updated = todo.copy(completed = !todo.completed)
            repository.updateTodo(updated)
        }
    }
}