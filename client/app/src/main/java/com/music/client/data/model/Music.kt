package com.music.client.data.model

import com.google.gson.annotations.SerializedName

data class Music(
    val id: Long,
    val fileName: String,
    val filePath: String,
    val fileSize: Long,
    val duration: Int?,
    val format: String,
    val artist: String?,
    val album: String?,
    val title: String?,
    val createdAt: String?,
    val updatedAt: String?
)
