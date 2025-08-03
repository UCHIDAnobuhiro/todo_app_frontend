package com.example.todoapp.data.model

import kotlinx.serialization.Serializable

/**
 * Todoアイテムを表すデータクラス。
 *
 * @property id Todoの一意な識別子。新規作成時にまだIDが割り当てられていない状態を示すためにデフォルトで0を使用。
 * @property title Todoの内容やタイトル。
 * @property completed Todoが完了しているかどうかを示すフラグ。
 */
@Serializable
data class Todo(
    val id: Int = 0,
    val title: String,
    val completed: Boolean
)
