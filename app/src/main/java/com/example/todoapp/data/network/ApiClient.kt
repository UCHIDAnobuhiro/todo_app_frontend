package com.example.todoapp.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Retrofitを使ってAPI通信を行うためのクライアントオブジェクト。
 *
 * - Kotlinx Serializationを使用してJSONを解析
 * - BASE_URLはエミュレータ上のローカルホスト向けに設定（10.0.2.2）
 */
@OptIn(ExperimentalSerializationApi::class)
object ApiClient {


    /** エミュレータから見たローカルホストのIPアドレス */
    private const val BASE_URL = "http://10.0.2.2:8080/"

    /**
     * Jsonの設定:
     * - `ignoreUnknownKeys`: キーが未知のJSONのプロパティを無視する
     */
    private val json = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Retrofitのインスタンス:
     * - `baseUrl`: APIエンドポイントのベースURL
     * - Kotlinx Serializationのコンバータを追加
     */
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    /**
     * TodoApiのインスタンス
     * Retrofitを使用してインスタンスを作成
     */
    val todoApi: TodoApi = retrofit.create(TodoApi::class.java)
}