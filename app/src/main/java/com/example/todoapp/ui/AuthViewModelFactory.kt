package com.example.todoapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.local.TokenStore
import com.example.todoapp.data.network.ApiClient
import com.example.todoapp.data.repository.AuthRepository
import com.example.todoapp.viewmodel.AuthViewModel

class AuthViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = AuthRepository(
            api = ApiClient.authApi,
            tokenStore = TokenStore(context),
            tokenProvider = ApiClient.tokenProvider
        )
        @Suppress("UNCHECKED_CAST")
        return AuthViewModel(repo) as T
    }
}