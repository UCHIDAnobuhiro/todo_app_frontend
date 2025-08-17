package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.navigation.AppNavGraph
import com.example.todoapp.ui.AuthViewModelFactory
import com.example.todoapp.viewmodel.AuthViewModel
import com.example.todoapp.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    private val viewModel = TodoViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authVm = ViewModelProvider(this, AuthViewModelFactory(this))[AuthViewModel::class.java]
        val todoVm = ViewModelProvider(this)[TodoViewModel::class.java]
        setContent {
            AppNavGraph(authVm, todoVm)
        }
    }
}
