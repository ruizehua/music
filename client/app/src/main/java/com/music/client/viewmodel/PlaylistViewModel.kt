package com.music.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.music.client.data.model.Music
import com.music.client.data.model.Playlist
import com.music.client.data.model.PlaylistDetail
import com.music.client.data.repository.PlaylistRepository
import com.music.client.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository,
    private val playerManager: MusicPlayerManager
) : ViewModel() {

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()

    private val _playlistDetail = MutableStateFlow<PlaylistDetail?>(null)
    val playlistDetail: StateFlow<PlaylistDetail?> = _playlistDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadPlaylists()
    }

    fun loadPlaylists() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            playlistRepository.getAllPlaylists().onSuccess { list ->
                _playlists.value = list
            }.onFailure { e ->
                _error.value = e.message
            }
            _isLoading.value = false
        }
    }

    fun loadPlaylistDetail(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            playlistRepository.getPlaylistById(id).onSuccess { detail ->
                _playlistDetail.value = detail
            }.onFailure { e ->
                _error.value = e.message
            }
            _isLoading.value = false
        }
    }

    fun createPlaylist(name: String, description: String? = null) {
        viewModelScope.launch {
            playlistRepository.createPlaylist(name, description).onSuccess {
                loadPlaylists()
            }.onFailure { e ->
                _error.value = e.message
            }
        }
    }

    fun updatePlaylist(id: Long, name: String, description: String? = null) {
        viewModelScope.launch {
            playlistRepository.updatePlaylist(id, name, description).onSuccess {
                loadPlaylists()
                loadPlaylistDetail(id)
            }.onFailure { e ->
                _error.value = e.message
            }
        }
    }

    fun deletePlaylist(id: Long) {
        viewModelScope.launch {
            playlistRepository.deletePlaylist(id).onSuccess {
                loadPlaylists()
                _playlistDetail.value = null
            }.onFailure { e ->
                _error.value = e.message
            }
        }
    }

    fun addSongToPlaylist(playlistId: Long, songId: Long) {
        viewModelScope.launch {
            playlistRepository.addSongToPlaylist(playlistId, songId).onSuccess {
                loadPlaylistDetail(playlistId)
            }.onFailure { e ->
                _error.value = e.message
            }
        }
    }

    fun removeSongFromPlaylist(playlistId: Long, songId: Long) {
        viewModelScope.launch {
            playlistRepository.removeSongFromPlaylist(playlistId, songId).onSuccess {
                loadPlaylistDetail(playlistId)
            }.onFailure { e ->
                _error.value = e.message
            }
        }
    }

    fun playPlaylist(detail: PlaylistDetail, startIndex: Int = 0) {
        val songs = detail.songs ?: return
        if (songs.isNotEmpty()) {
            playerManager.setPlaylist(songs, startIndex)
        }
    }
}
