package com.example.todoapp.data.repository

import com.example.todoapp.data.auth.TokenProvider
import com.example.todoapp.data.local.TokenStore
import com.example.todoapp.data.model.LoginRequest
import com.example.todoapp.data.model.LoginResponse
import com.example.todoapp.data.network.AuthApi
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryTest {
    private val api: AuthApi = mockk()
    private val tokenStore: TokenStore = mockk(relaxed = true)
    private val tokenProvider: TokenProvider = mockk(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repo: AuthRepository

    @Before
    fun setUp() {
        repo = AuthRepository(
            api = api,
            tokenStore = tokenStore,
            tokenProvider = tokenProvider,
            io = testDispatcher
        )
    }

    @Test
    fun `login success - updates provider, saves token, returns response`() =
        runTest(testDispatcher) {
            val email = "test@example.com"
            val password = "password"
            val response = LoginResponse(token = "token_123")

            coEvery { api.login(LoginRequest(email, password)) } returns response
            coEvery { tokenStore.save("token_123") } just runs

            val result = repo.login(email, password)

            assertThat(result).isEqualTo(response)
            coVerify(exactly = 1) { tokenProvider.update("token_123") }
            coVerify(exactly = 1) { tokenStore.save("token_123") }
            coVerify(exactly = 1) { api.login(LoginRequest(email, password)) }
        }

    @Test(expected = IOException::class)
    fun `login fails when token store save throws - provider remains updated in current impl`() =
        runTest(testDispatcher) {
            // 現在の実装では save が失敗すると例外が伝播し、ロールバックはしない
            val email = "test@example.com"
            val pass = "password"
            val response = LoginResponse(token = "token123")

            coEvery { api.login(LoginRequest(email, pass)) } returns response
            coEvery { tokenStore.save("token123") } throws IOException("disk full")

            try {
                repo.login(email, pass)
            } finally {
                // tokenProvider.update は呼ばれている（ロールバックなし想定）
                coVerify(exactly = 1) { tokenProvider.update("token123") }
                coVerify(exactly = 1) { tokenStore.save("token123") }
            }
        }

    @Test
    fun `logout - clears provider and store`() = runTest(testDispatcher) {
        // given
        coEvery { tokenProvider.clear() } just runs
        coEvery { tokenStore.clear() } just runs

        // when
        repo.logout()

        // then
        coVerify(exactly = 1) { tokenProvider.clear() }
        coVerify(exactly = 1) { tokenStore.clear() }
    }
}