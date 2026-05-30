package com.music.client.data.api

import com.music.client.data.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface MusicApi {
    @GET("music")
    suspend fun getMusicList(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("keyword") keyword: String? = null,
        @Query("artist") artist: String? = null,
        @Query("album") album: String? = null
    ): ApiResponse<PagedResult<Music>>

    @GET("music/{id}")
    suspend fun getMusicById(@Path("id") id: Long): ApiResponse<Music>

    @GET("music/{id}/stream")
    @Streaming
    suspend fun streamMusic(@Path("id") id: Long): retrofit2.Response<okhttp3.ResponseBody>

    @GET("music/search")
    suspend fun searchMusic(
        @Query("keyword") keyword: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): ApiResponse<List<Music>>

    @Multipart
    @POST("music/upload")
    suspend fun uploadMusic(@Part file: MultipartBody.Part): ApiResponse<Music>

    @DELETE("music/{id}")
    suspend fun deleteMusic(@Path("id") id: Long): ApiResponse<Unit>
}
