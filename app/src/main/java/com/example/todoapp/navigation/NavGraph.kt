package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.screen.AddTodoScreen
import com.example.todoapp.ui.screen.TodoListScreen
import com.example.todoapp.viewmodel.TodoViewModel

/**
 * アプリ全体のナビゲーショングラフを定義するComposable。
 *
 * @param viewModel Todoデータの状態管理を行う [TodoViewModel]。
 *
 * この関数では、以下の画面遷移を定義しています：
 * - "list": Todo一覧画面 [TodoListScreen]
 * - "add" : Todo追加画面 [AddTodoScreen]
 */
@Composable
fun AppNavGraph(viewModel: TodoViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {
        composable("list") {
            TodoListScreen(navController, viewModel)
        }
        composable("add") {
            AddTodoScreen(navController, viewModel)
        }
    }
}