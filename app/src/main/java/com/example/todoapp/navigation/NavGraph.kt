package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.screen.AddTodoScreen
import com.example.todoapp.ui.screen.TodoListScreen
import com.example.todoapp.viewmodel.TodoViewModel

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