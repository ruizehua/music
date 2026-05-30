package com.music.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.music.client.data.model.PlayHistory
import com.music.client.data.repository.PlayHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val playHistoryRepository: PlayHistoryRepository
) : ViewModel() {

    private val _historyList = MutableStateFlow<List<PlayHistory>>(emptyList())
    val historyList: StateFlow<List<PlayHistory>> = _historyList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadHistory()
    }

    fun loadHistory(page: Int = 0) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            playHistoryRepository.getPlayHistory(page).onSuccess { list ->
                _historyList.value = list
            }.onFailure { e ->
                _error.value = e.message
            }
            _isLoading.value = false
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            playHistoryRepository.clearPlayHistory().onSuccess {
                _historyList.value = emptyList()
            }.onFailure { e ->
                _error.value = e.message
            }
        }
    }
}
