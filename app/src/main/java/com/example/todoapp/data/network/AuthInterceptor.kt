package com.example.todoapp.data.network

import com.example.todoapp.data.auth.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val provider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val t = provider.getToken()
        val req = if (!t.isNullOrBlank()) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $t")
                .build()
        } else chain.request()
        return chain.proceed(req)
    }
}