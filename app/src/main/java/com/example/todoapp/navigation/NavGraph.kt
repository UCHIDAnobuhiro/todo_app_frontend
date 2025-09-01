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

// Routesは、画面遷移の識別子です。
object Routes {
    const val LOGIN = "login"
    const val LIST = "list"
    const val ADD = "add"
}

/**
 * アプリ全体のナビゲーショングラフを定義するComposable。
 *
 * @param authViewModel ログイン認証の状態管理を行う [TodoViewModel]。
 * @param todoViewModel リストデータの状態管理を行う [TodoViewModel]。
 *
 */
@Composable
fun AppNavGraph(
    authViewModel: AuthViewModel,
    todoViewModel: TodoViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                authViewModel,
                onLoggedIn = {
                    todoViewModel.fetchTodos()
                    // ログイン成功で一覧画面に遷移し、戻るでログインに戻れないように消す
                    navController.navigate(Routes.LIST) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Routes.LIST) {
            TodoListScreen(
                navController,
                todoViewModel,
                onLogout = {
                    // ログアウト処理
                    authViewModel.logout() // ← TokenStore削除とか
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true } // backstack 全消し
                    }
                }
            )
        }
        composable(Routes.ADD) {
            AddTodoScreen(
                navController,
                todoViewModel,
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}