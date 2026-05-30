package com.music.client.data.model

data class PlayHistory(
    val id: Long?,
    val songId: Long,
    val fileName: String?,
    val artist: String?,
    val title: String?,
    val playTime: Int?,
    val createdAt: String?
)
