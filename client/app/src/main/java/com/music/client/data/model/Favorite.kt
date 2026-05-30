package com.music.client.data.model

data class Favorite(
    val id: Long?,
    val songId: Long,
    val fileName: String?,
    val artist: String?,
    val title: String?,
    val createdAt: String?
)
