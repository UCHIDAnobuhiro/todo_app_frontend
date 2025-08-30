package com.example.todoapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    vm: AuthViewModel,
    onLoggedIn: () -> Unit,
) {
    val ui by vm.ui.collectAsState()

    // 成功時に画面遷移（1回だけ）
    LaunchedEffect(ui.success) {
        if (ui.success) onLoggedIn()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.login), style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = ui.email,
            onValueChange = vm::onEmailChange,
            label = { Text(stringResource(R.string.mail)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = ui.password,
            onValueChange = vm::onPasswordChange,
            label = { Text(stringResource(R.string.password)) },
            singleLine = true,
            visualTransformation = if (ui.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = vm::togglePassword) {
                    Icon(
                        imageVector = if (ui.isPasswordVisible) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = if (ui.isPasswordVisible) stringResource(R.string.hide) else stringResource(
                            R.string.show
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (ui.error != null) {
            Spacer(Modifier.height(12.dp))
            Text(ui.error!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = vm::login,
            enabled = !ui.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (ui.isLoading) CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
            )
            else Text(stringResource(R.string.login))
        }
    }
}