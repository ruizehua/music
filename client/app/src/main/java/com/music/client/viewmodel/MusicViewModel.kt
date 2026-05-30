package com.music.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.music.client.data.model.Music
import com.music.client.data.model.PagedResult
import com.music.client.data.repository.MusicRepository
import com.music.client.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    private val playerManager: MusicPlayerManager
) : ViewModel() {

    private val _musicList = MutableStateFlow<List<Music>>(emptyList())
    val musicList: StateFlow<List<Music>> = _musicList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _totalPages = MutableStateFlow(1)
    val totalPages: StateFlow<Int> = _totalPages.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedArtist = MutableStateFlow<String?>(null)
    val selectedArtist: StateFlow<String?> = _selectedArtist.asStateFlow()

    private val _selectedAlbum = MutableStateFlow<String?>(null)
    val selectedAlbum: StateFlow<String?> = _selectedAlbum.asStateFlow()

    init {
        loadMusicList()
    }

    fun loadMusicList(page: Int = 0) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            musicRepository.getMusicList(
                page = page,
                keyword = _searchQuery.value.ifBlank { null },
                artist = _selectedArtist.value,
                album = _selectedAlbum.value
            ).onSuccess { result ->
                _musicList.value = result.content
                _currentPage.value = result.currentPage
                _totalPages.value = result.totalPages
            }.onFailure { e ->
                _error.value = e.message
            }
            _isLoading.value = false
        }
    }

    fun searchMusic(query: String) {
        _searchQuery.value = query
        loadMusicList(0)
    }

    fun filterByArtist(artist: String?) {
        _selectedArtist.value = artist
        loadMusicList(0)
    }

    fun filterByAlbum(album: String?) {
        _selectedAlbum.value = album
        loadMusicList(0)
    }

    fun playMusic(music: Music, playlist: List<Music> = emptyList()) {
        val playList = if (playlist.isEmpty()) _musicList.value else playlist
        val index = playList.indexOf(music)
        playerManager.setPlaylist(playList, if (index >= 0) index else 0)
    }

    fun loadNextPage() {
        if (_currentPage.value < _totalPages.value - 1) {
            loadMusicList(_currentPage.value + 1)
        }
    }

    fun loadPreviousPage() {
        if (_currentPage.value > 0) {
            loadMusicList(_currentPage.value - 1)
        }
    }

    fun deleteMusic(id: Long) {
        viewModelScope.launch {
            musicRepository.deleteMusic(id).onSuccess {
                loadMusicList(_currentPage.value)
            }.onFailure { e ->
                _error.value = e.message
            }
        }
    }
}
