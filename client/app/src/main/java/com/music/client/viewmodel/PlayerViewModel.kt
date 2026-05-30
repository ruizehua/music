package com.music.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.music.client.data.model.Favorite
import com.music.client.data.model.Music
import com.music.client.data.model.PlayMode
import com.music.client.data.model.PlaybackState
import com.music.client.data.repository.FavoriteRepository
import com.music.client.data.repository.PlayHistoryRepository
import com.music.client.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerManager: MusicPlayerManager,
    private val favoriteRepository: FavoriteRepository,
    private val playHistoryRepository: PlayHistoryRepository
) : ViewModel() {

    val playbackState: StateFlow<PlaybackState> = playerManager.playbackState
    val currentMusic: StateFlow<Music?> = playerManager.currentMusic
    val currentPosition: StateFlow<Long> = playerManager.currentPosition
    val duration: StateFlow<Long> = playerManager.duration
    val playMode: StateFlow<PlayMode> = playerManager.playMode
    val playlist: StateFlow<List<Music>> = playerManager.playlist
    val currentIndex: StateFlow<Int> = playerManager.currentIndex

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites: StateFlow<List<Favorite>> = _favorites.asStateFlow()

    init {
        startProgressUpdate()
        loadFavorites()
    }

    private fun startProgressUpdate() {
        viewModelScope.launch {
            while (true) {
                playerManager.updatePosition()
                delay(500)
            }
        }
    }

    fun playOrPause() {
        playerManager.playOrPause()
    }

    fun playNext() {
        playerManager.playNext()
        checkFavoriteStatus()
    }

    fun playPrevious() {
        playerManager.playPrevious()
        checkFavoriteStatus()
    }

    fun seekTo(positionMs: Long) {
        playerManager.seekTo(positionMs)
    }

    fun togglePlayMode() {
        playerManager.togglePlayMode()
    }

    fun toggleFavorite() {
        val music = currentMusic.value ?: return
        viewModelScope.launch {
            if (_isFavorite.value) {
                favoriteRepository.removeFavorite(music.id)
            } else {
                favoriteRepository.addFavorite(music.id)
            }
            checkFavoriteStatus()
            loadFavorites()
        }
    }

    private fun checkFavoriteStatus() {
        val music = currentMusic.value ?: return
        viewModelScope.launch {
            favoriteRepository.isFavorite(music.id).onSuccess { isFav ->
                _isFavorite.value = isFav
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            favoriteRepository.getAllFavorites().onSuccess { list ->
                _favorites.value = list
            }
        }
    }

    fun recordPlayHistory() {
        val music = currentMusic.value ?: return
        viewModelScope.launch {
            playHistoryRepository.addPlayHistory(music.id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        recordPlayHistory()
    }
}
