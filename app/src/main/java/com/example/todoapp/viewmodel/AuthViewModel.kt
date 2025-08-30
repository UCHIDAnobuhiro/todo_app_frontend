package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException


/**
 * ログイン画面の状態を保持するデータクラス。
 *
 * @property email 入力されたメールアドレス
 * @property password 入力されたパスワード
 * @property isPasswordVisible パスワード表示/非表示フラグ
 * @property isLoading ログイン処理中かどうか
 * @property error エラーメッセージ（null の場合はエラーなし）
 * @property success ログイン成功フラグ
 */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

/**
 * ログイン処理およびログイン画面の状態を管理する [ViewModel]。
 *
 * - 入力値の検証
 * - [AuthRepository] を利用したログイン処理
 * - [LoginUiState] の更新
 *
 * @param repo 認証リポジトリ。ログインAPIを呼び出す責務を持つ。
 */
class AuthViewModel(private val repo: AuthRepository) : ViewModel() {
    private val _ui = MutableStateFlow(LoginUiState())
    val ui: StateFlow<LoginUiState> = _ui

    /**
     * メールアドレス入力変更時に状態を更新する。
     * @param email 入力されたメールアドレス
     */
    fun onEmailChange(email: String) {
        _ui.update { it.copy(email = email, error = null) }
    }

    /**
     * パスワード入力変更時に状態を更新する。
     * @param password 入力されたパスワード
     */
    fun onPasswordChange(password: String) {
        _ui.update { it.copy(password = password, error = null) }
    }

    /**
     * パスワードの表示/非表示を切り替える。
     */
    fun togglePassword() {
        _ui.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    /**
     * ログイン処理を実行する。
     *
     * 入力チェック後、[AuthRepository] を通じてログインを行い、
     * 成功/失敗に応じて [LoginUiState] を更新する。
     */
    fun login() {
        val (email, password) = _ui.value.let { it.email to it.password }

        //　validation
        if (email.isBlank() || password.isBlank()) {
            _ui.update { it.copy(error = "メールアドレスとパスワードを入力してください") }
            return
        }

        if (!email.contains("@")) {
            _ui.update { it.copy(error = "メールアドレスの形式が不正です") }
            return
        }

        if (password.length < 8) {
            _ui.update { it.copy(error = "パスワードは8文字以上") }
            return
        }

        // 非同期でログイン処理
        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true, error = null, success = false) }
            try {
                repo.login(email, password)
                _ui.update { it.copy(isLoading = false, success = true) }
            } catch (e: HttpException) {
                Log.e("LoginVM", "http error", e)
                _ui.update {
                    it.copy(
                        isLoading = false,
                        error = if (e.code() == 401) "メールまたはパスワードが違います" else "HTTPエラー: ${e.code()}"
                    )
                }
            } catch (e: IOException) {
                // ネットワーク未到達（タイムアウト/UnknownHost 等）
                Log.e("LoginVM", "io error", e)
                _ui.update {
                    it.copy(
                        isLoading = false,
                        error = "通信エラー: ネットワークを確認してください"
                    )
                }
            } catch (e: SerializationException) {
                Log.e("LoginVM", "json error", e)
                _ui.update {
                    it.copy(
                        isLoading = false,
                        error = "JSONエラー: レスポンス形式が不正です"
                    )
                }
            } catch (e: Exception) {
                Log.e("LoginVM", "unknown error", e)
                _ui.update {
                    it.copy(
                        isLoading = false,
                        error = "不明なエラー: ${e.message}"
                    )
                }
            }
        }
    }
}