package com.example.todoapp.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.todoapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  TodoListHeader() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_header_text)) },
    )
}