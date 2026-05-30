package com.music.client.data.repository

import com.music.client.data.api.PlayHistoryApi
import com.music.client.data.model.PlayHistory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayHistoryRepository @Inject constructor(
    private val playHistoryApi: PlayHistoryApi
) {
    suspend fun getPlayHistory(page: Int = 0, size: Int = 20): Result<List<PlayHistory>> {
        return try {
            val response = playHistoryApi.getPlayHistory(page, size)
            if (response.code == 200 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "获取播放历史失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addPlayHistory(songId: Long, playTime: Int? = null): Result<Unit> {
        return try {
            val request = mutableMapOf<String, Any>("songId" to songId)
            playTime?.let { request["playTime"] = it }
            val response = playHistoryApi.addPlayHistory(request)
            if (response.code == 200) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message ?: "添加播放历史失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun clearPlayHistory(): Result<Unit> {
        return try {
            val response = playHistoryApi.clearPlayHistory()
            if (response.code == 200) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message ?: "清空播放历史失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
