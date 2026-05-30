package com.music.client.data.api

import com.music.client.data.model.*
import retrofit2.http.*

interface FavoriteApi {
    @GET("favorites")
    suspend fun getAllFavorites(): ApiResponse<List<Favorite>>

    @POST("favorites/{songId}")
    suspend fun addFavorite(@Path("songId") songId: Long): ApiResponse<Unit>

    @DELETE("favorites/{songId}")
    suspend fun removeFavorite(@Path("songId") songId: Long): ApiResponse<Unit>

    @GET("favorites/check/{songId}")
    suspend fun checkFavorite(@Path("songId") songId: Long): ApiResponse<Map<String, Boolean>>
}
