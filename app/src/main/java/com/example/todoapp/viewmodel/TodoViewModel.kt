package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Todoデータの状態管理とUIとの橋渡しを行うViewModel。
 *
 * [TodoRepository] を通じてTodoの取得・追加・更新・削除を行い、
 * UIには [StateFlow] を通じてリアクティブに状態を提供する。
 */
class TodoViewModel (private val repository: TodoRepository = TodoRepository()) : ViewModel() {

    val todos: StateFlow<List<Todo>> = repository.todos

    init {
        // ViewModel生成時にTodo一覧を取得
        viewModelScope.launch {
            repository.fetchTodos()
        }
    }

    /**
     * 新しいTodoを追加する。
     *
     * @param title 追加するTodoのタイトル。
     */
    fun addTodo(title: String) {
        viewModelScope.launch {
            repository.addTodo(title)
        }
    }

    /**
     * Todoの完了状態を反転させて更新する。
     *
     * @param todo 完了状態を切り替える対象のTodo。
     */
    fun toggleCompleted(todo: Todo) {
        viewModelScope.launch {
            val updated = todo.copy(completed = !todo.completed)
            repository.updateTodo(updated)
        }
    }

    /**
     * 指定されたTodoを削除する。
     *
     * @param todo 削除対象のTodo。
     */
    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }
}