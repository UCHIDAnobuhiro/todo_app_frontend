package com.example.todoapp.data.auth

interface TokenProvider {
    fun getToken(): String?
}

class InMemoryTokenProvider : TokenProvider {
    @Volatile
    private var token: String? = null

    fun update(t: String) {
        token = t
    }

    fun clear() {
        token = null
    }

    override fun getToken(): String? = token
}