package com.music.client.data.model

data class ApiResponse<T>(
    val code: Int,
    val message: String?,
    val data: T?
)
