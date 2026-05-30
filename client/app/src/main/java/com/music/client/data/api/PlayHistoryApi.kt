package com.music.client.data.api

import com.music.client.data.model.*
import retrofit2.http.*

interface PlayHistoryApi {
    @GET("history")
    suspend fun getPlayHistory(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): ApiResponse<List<PlayHistory>>

    @POST("history")
    suspend fun addPlayHistory(@Body request: Map<String, @JvmSuppressWildcards Any>): ApiResponse<Unit>

    @DELETE("history")
    suspend fun clearPlayHistory(): ApiResponse<Unit>
}
