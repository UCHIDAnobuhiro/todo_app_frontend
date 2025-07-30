package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.todoapp.theme.TodoAppTheme
import com.example.todoapp.viewmodel.TodoViewModel
import com.example.todoapp.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    private val viewModel = TodoViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme {
                AppNavGraph(viewModel)
            }
        }
    }
}
