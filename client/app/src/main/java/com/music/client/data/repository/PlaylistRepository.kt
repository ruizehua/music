package com.music.client.data.repository

import com.music.client.data.api.PlaylistApi
import com.music.client.data.model.Playlist
import com.music.client.data.model.PlaylistDetail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaylistRepository @Inject constructor(
    private val playlistApi: PlaylistApi
) {
    suspend fun getAllPlaylists(): Result<List<Playlist>> {
        return try {
            val response = playlistApi.getAllPlaylists()
            if (response.code == 200 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "获取歌单列表失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPlaylistById(id: Long): Result<PlaylistDetail> {
        return try {
            val response = playlistApi.getPlaylistById(id)
            if (response.code == 200 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "获取歌单详情失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createPlaylist(name: String, description: String? = null): Result<Playlist> {
        return try {
            val request = mutableMapOf("name" to name)
            description?.let { request["description"] = it }
            val response = playlistApi.createPlaylist(request)
            if (response.code == 200 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "创建歌单失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePlaylist(id: Long, name: String, description: String? = null): Result<Playlist> {
        return try {
            val request = mutableMapOf("name" to name)
            description?.let { request["description"] = it }
            val response = playlistApi.updatePlaylist(id, request)
            if (response.code == 200 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "更新歌单失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deletePlaylist(id: Long): Result<Unit> {
        return try {
            val response = playlistApi.deletePlaylist(id)
            if (response.code == 200) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message ?: "删除歌单失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addSongToPlaylist(playlistId: Long, songId: Long): Result<Unit> {
        return try {
            val response = playlistApi.addSongToPlaylist(playlistId, mapOf("songId" to songId))
            if (response.code == 200) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message ?: "添加歌曲失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removeSongFromPlaylist(playlistId: Long, songId: Long): Result<Unit> {
        return try {
            val response = playlistApi.removeSongFromPlaylist(playlistId, songId)
            if (response.code == 200) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message ?: "移除歌曲失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
