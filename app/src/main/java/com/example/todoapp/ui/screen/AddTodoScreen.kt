package com.example.todoapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.viewmodel.TodoViewModel


@Composable
fun AddTodoScreen(navController: NavController, viewModel: TodoViewModel) {
    var title by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        TextField(value = title, onValueChange = { title = it }, label = { Text("新しいTodo") })
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            if (title.isNotBlank()) {
                viewModel.addTodo(title)
                navController.popBackStack()
            }
        }) {
            Text("追加")
        }
    }
}