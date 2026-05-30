package com.music.client.data.model

data class PlaylistDetail(
    val id: Long,
    val name: String,
    val description: String?,
    val songCount: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val songs: List<Music>?
)
