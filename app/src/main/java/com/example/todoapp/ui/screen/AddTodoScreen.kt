package com.example.todoapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.ui.component.AddTodoHeader
import com.example.todoapp.viewmodel.TodoViewModel


/**
 * Todoを新規作成する画面。
 *
 * ユーザーがタイトルを入力し、「追加」ボタンを押すと、
 * Todoが作成され、前の画面（リスト）に戻る。
 *
 * @param navController ナビゲーション操作を行うためのコントローラ。
 * @param viewModel Todoの状態を管理するViewModel。
 */
@Composable
fun AddTodoScreen(navController: NavController, viewModel: TodoViewModel) {
    var title by remember { mutableStateOf("") }

    Scaffold(
        topBar = { AddTodoHeader { navController.popBackStack()} }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.add_task_label_text)) }
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                if (title.isNotBlank()) {
                    viewModel.addTodo(title)
                    navController.popBackStack()
                }
            }) {
                Text(stringResource(R.string.add_task_btn_text))
            }
        }
    }
}