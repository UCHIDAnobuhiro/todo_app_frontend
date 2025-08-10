package com.example.todoapp.data.repository

import android.util.Log
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.network.ApiClient
import com.example.todoapp.data.network.TodoApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Todoのデータ管理を行うリポジトリクラス。
 * APIとの通信を通じてデータの取得・追加・更新・削除を行い、
 * Todoリストの状態をStateFlowとして保持・提供する。
 */
class TodoRepository(private val api: TodoApi = ApiClient.todoApi) {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())

    /**
     * Todoリストの最新状態を通知する読み取り専用のStateFlow。
     */
    val todos: StateFlow<List<Todo>> = _todos


    /**
     * サーバーからTodo一覧を取得し、StateFlowを更新する。
     */
    suspend fun fetchTodos() {
        try {
            val result = api.getTodos()
            _todos.value = result
        } catch (e: Exception) {
            // エラーハンドリング（ログやUI通知）
            e.printStackTrace()
        }
    }

    /**
     * 新しいTodoをサーバーに追加し、一覧を再取得する。
     *
     * @param title 追加するTodoのタイトル。
     */
    suspend fun addTodo(title: String) {
        try {
            val newTodo = Todo(title = title, completed = false)
            api.addTodo(newTodo)
            _todos.value = api.getTodos()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 指定されたTodoを更新し、一覧を再取得する。
     *
     * @param todo 更新対象のTodoオブジェクト。
     */
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

    /**
     * 指定されたTodoを削除し、一覧を再取得する。
     *
     * @param todo 削除対象のTodoオブジェクト。
     */
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