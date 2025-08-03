package com.example.todoapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.example.todoapp.viewmodel.TodoViewModel

/**
 * Todoの一覧を表示する画面。
 *
 * - チェックボックスで完了状態を切り替え可能。
 * - ゴミ箱アイコンでTodoの削除が可能。
 * - FAB（＋ボタン）から新規作成画面へ遷移。
 *
 * @param navController 画面遷移を制御するためのNavController。
 * @param viewModel Todoの状態管理を行うViewModel。
 */
@Composable
fun TodoListScreen(navController: NavController, viewModel: TodoViewModel) {
    val todos by viewModel.todos.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add") }) {
                Icon(Icons.Default.Add, contentDescription = "追加")
            }
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(todos) { todo ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // チェックボックスで完了状態を切り替え
                    Checkbox(
                        checked = todo.completed,
                        onCheckedChange = {
                            viewModel.toggleCompleted(todo)
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    // タイトル表示（完了状態で取り消し線）
                    Text(
                        todo.title,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = if (todo.completed) TextDecoration.LineThrough else TextDecoration.None
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    // 削除アイコン
                    IconButton(onClick = { viewModel.deleteTodo(todo) }) {
                        Icon(Icons.Default.Delete, contentDescription = "削除")
                    }
                }
            }
        }
    }
}