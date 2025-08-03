package com.example.todoapp.data.network

import com.example.todoapp.data.model.Todo
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofitを使用してTodoリソースにアクセスするためのAPIインターフェース。
 */
interface TodoApi {

    /**
     * APIレスポンスで返されるメッセージを表すデータクラス。
     *
     * @property message 操作結果のメッセージ。
     */
    @Serializable
    data class MessageResponse(val message: String)


    /**
     * Todo一覧を取得する。
     *
     * @return Todoのリスト。
     */
    @GET("/todos")
    suspend fun getTodos(): List<Todo>

    /**
     * 新しいTodoを追加する。
     *
     * @param todo 追加するTodoオブジェクト。
     * @return 操作結果を含む [MessageResponse]。
     */
    @POST("/todos")
    suspend fun addTodo(@Body todo: Todo): MessageResponse

    /**
     * 指定したIDのTodoを更新する。
     *
     * @param id 更新対象のTodoのID。
     * @param todo 更新後のTodoオブジェクト。
     * @return 操作結果を含む [MessageResponse]。
     */
    @PUT("/todos/{id}")
    suspend fun updateTodo(@Path("id") id: Int, @Body todo: Todo): MessageResponse

    /**
     * 指定したIDのTodoを削除する。
     *
     * @param id 削除対象のTodoのID。
     * @return 操作結果を含む [MessageResponse]。
     */
    @DELETE("/todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int): MessageResponse
}