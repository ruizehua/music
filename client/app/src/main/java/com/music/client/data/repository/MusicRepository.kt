package com.music.client.data.repository

import com.music.client.data.api.MusicApi
import com.music.client.data.model.Music
import com.music.client.data.model.PagedResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepository @Inject constructor(
    private val musicApi: MusicApi
) {
    suspend fun getMusicList(
        page: Int = 0,
        size: Int = 20,
        keyword: String? = null,
        artist: String? = null,
        album: String? = null
    ): Result<PagedResult<Music>> {
        return try {
            val response = musicApi.getMusicList(page, size, keyword, artist, album)
            if (response.code == 200 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "获取音乐列表失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMusicById(id: Long): Result<Music> {
        return try {
            val response = musicApi.getMusicById(id)
            if (response.code == 200 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "获取音乐详情失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchMusic(keyword: String, page: Int = 0, size: Int = 20): Result<List<Music>> {
        return try {
            val response = musicApi.searchMusic(keyword, page, size)
            if (response.code == 200 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "搜索失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteMusic(id: Long): Result<Unit> {
        return try {
            val response = musicApi.deleteMusic(id)
            if (response.code == 200) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message ?: "删除失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getStreamUrl(id: Long): String {
        return "http://10.0.2.2:8080/api/v1/music/$id/stream"
    }
}
