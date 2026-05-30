package com.music.client.data.api

import com.music.client.data.model.*
import retrofit2.http.*

interface PlaylistApi {
    @GET("playlists")
    suspend fun getAllPlaylists(): ApiResponse<List<Playlist>>

    @GET("playlists/{id}")
    suspend fun getPlaylistById(@Path("id") id: Long): ApiResponse<PlaylistDetail>

    @POST("playlists")
    suspend fun createPlaylist(@Body request: Map<String, String>): ApiResponse<Playlist>

    @PUT("playlists/{id}")
    suspend fun updatePlaylist(
        @Path("id") id: Long,
        @Body request: Map<String, String>
    ): ApiResponse<Playlist>

    @DELETE("playlists/{id}")
    suspend fun deletePlaylist(@Path("id") id: Long): ApiResponse<Unit>

    @POST("playlists/{id}/songs")
    suspend fun addSongToPlaylist(
        @Path("id") id: Long,
        @Body request: Map<String, Long>
    ): ApiResponse<Unit>

    @DELETE("playlists/{id}/songs/{songId}")
    suspend fun removeSongFromPlaylist(
        @Path("id") id: Long,
        @Path("songId") songId: Long
    ): ApiResponse<Unit>
}
