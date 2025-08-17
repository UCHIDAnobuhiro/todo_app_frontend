package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.screen.AddTodoScreen
import com.example.todoapp.ui.screen.LoginScreen
import com.example.todoapp.ui.screen.TodoListScreen
import com.example.todoapp.viewmodel.AuthViewModel
import com.example.todoapp.viewmodel.TodoViewModel

object Routes {
    const val Login = "login"
    const val List = "list"
    const val Add = "add"
}

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
fun AppNavGraph(
    authViewModel: AuthViewModel,
    todoViewModel: TodoViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Login
    ) {
        composable(Routes.Login) {
            LoginScreen(
                vm = authViewModel,
                onLoggedIn = {
                    // ログイン成功でTodo一覧画面に遷移し、戻るでログインに戻れないように消す
                    navController.navigate(Routes.List) {
                        popUpTo(Routes.Login) { inclusive = true }
                        launchSingleTop = true
                    }

                }
            )
        }
        composable(Routes.List) {
            TodoListScreen(navController, todoViewModel)
        }
        composable(Routes.Add) {
            AddTodoScreen(navController, todoViewModel)
        }
    }
}