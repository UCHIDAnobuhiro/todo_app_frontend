package com.example.todoapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun AddTodoScreen(navController: NavController, viewModel: TodoViewModel, onLogout: () -> Unit) {
    var title by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AddTodoHeader(
                onBack = { navController.popBackStack() },
                onLogout = onLogout
            )
        }
    ) { paddingValues ->
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