package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TokenStore
import com.example.todoapp.data.model.LoginRequest
import com.example.todoapp.data.model.LoginResponse
import com.example.todoapp.data.network.ApiClient
import com.example.todoapp.data.network.AuthApi

class AuthRepository(
    private val api: AuthApi,
    private val tokenStore: TokenStore
) {
    suspend fun login(email: String, password: String): LoginResponse {
        val res = api.login(LoginRequest(email, password))
        // メモリに反映（即時に Authorization を付与できる）
        ApiClient.tokenProvider.update(res.token)
        // 永続化（再起動後も使える）
        tokenStore.save(res.token)
        return res
    }

    suspend fun logout() {
        ApiClient.tokenProvider.clear()
        tokenStore.clear()
    }
}