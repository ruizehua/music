package com.music.client.data.model

data class Playlist(
    val id: Long,
    val name: String,
    val description: String?,
    val songCount: Int?,
    val createdAt: String?,
    val updatedAt: String?
)
