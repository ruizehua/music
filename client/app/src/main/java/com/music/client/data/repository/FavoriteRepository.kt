package com.music.client.data.repository

import com.music.client.data.api.FavoriteApi
import com.music.client.data.model.Favorite
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(
    private val favoriteApi: FavoriteApi
) {
    suspend fun getAllFavorites(): Result<List<Favorite>> {
        return try {
            val response = favoriteApi.getAllFavorites()
            if (response.code == 200 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "获取收藏列表失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addFavorite(songId: Long): Result<Unit> {
        return try {
            val response = favoriteApi.addFavorite(songId)
            if (response.code == 200) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message ?: "添加收藏失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removeFavorite(songId: Long): Result<Unit> {
        return try {
            val response = favoriteApi.removeFavorite(songId)
            if (response.code == 200) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message ?: "取消收藏失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun isFavorite(songId: Long): Result<Boolean> {
        return try {
            val response = favoriteApi.checkFavorite(songId)
            if (response.code == 200 && response.data != null) {
                Result.success(response.data["isFavorite"] ?: false)
            } else {
                Result.failure(Exception(response.message ?: "检查收藏状态失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
