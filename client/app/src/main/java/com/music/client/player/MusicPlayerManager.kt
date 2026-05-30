package com.music.client.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.music.client.data.model.Music
import com.music.client.data.model.PlayMode
import com.music.client.data.model.PlaybackState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val player: ExoPlayer = ExoPlayer.Builder(context).build()

    private val _playbackState = MutableStateFlow(PlaybackState.IDLE)
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private val _currentMusic = MutableStateFlow<Music?>(null)
    val currentMusic: StateFlow<Music?> = _currentMusic.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _playMode = MutableStateFlow(PlayMode.SEQUENTIAL)
    val playMode: StateFlow<PlayMode> = _playMode.asStateFlow()

    private val _playlist = MutableStateFlow<List<Music>>(emptyList())
    val playlist: StateFlow<List<Music>> = _playlist.asStateFlow()

    private val _currentIndex = MutableStateFlow(-1)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private var progressUpdateJob: kotlinx.coroutines.Job? = null

    private val listener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            _playbackState.value = when (playbackState) {
                Player.STATE_IDLE -> PlaybackState.IDLE
                Player.STATE_BUFFERING -> PlaybackState.BUFFERING
                Player.STATE_READY -> if (player.isPlaying) PlaybackState.PLAYING else PlaybackState.PAUSED
                Player.STATE_ENDED -> {
                    playNext()
                    PlaybackState.IDLE
                }
                else -> PlaybackState.IDLE
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (player.playbackState == Player.STATE_READY) {
                _playbackState.value = if (isPlaying) PlaybackState.PLAYING else PlaybackState.PAUSED
            }
        }
    }

    init {
        player.addListener(listener)
        player.prepare()
    }

    fun setPlaylist(musicList: List<Music>, startIndex: Int = 0) {
        _playlist.value = musicList
        _currentIndex.value = startIndex
        if (musicList.isNotEmpty() && startIndex in musicList.indices) {
            play(musicList[startIndex])
        }
    }

    fun play(music: Music) {
        _currentMusic.value = music
        val streamUrl = "http://10.0.2.2:8080/api/v1/music/${music.id}/stream"
        val mediaItem = MediaItem.fromUri(streamUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
    }

    fun playOrPause() {
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    fun playNext() {
        val list = _playlist.value
        val index = _currentIndex.value
        if (list.isEmpty()) return

        val nextIndex = when (_playMode.value) {
            PlayMode.REPEAT_ONE -> index
            PlayMode.SHUFFLE -> (0 until list.size).random()
            PlayMode.SEQUENTIAL -> (index + 1) % list.size
        }
        _currentIndex.value = nextIndex
        play(list[nextIndex])
    }

    fun playPrevious() {
        val list = _playlist.value
        val index = _currentIndex.value
        if (list.isEmpty()) return

        val prevIndex = when (_playMode.value) {
            PlayMode.REPEAT_ONE -> index
            PlayMode.SHUFFLE -> (0 until list.size).random()
            PlayMode.SEQUENTIAL -> if (index > 0) index - 1 else list.size - 1
        }
        _currentIndex.value = prevIndex
        play(list[prevIndex])
    }

    fun seekTo(positionMs: Long) {
        player.seekTo(positionMs)
    }

    fun togglePlayMode() {
        _playMode.value = when (_playMode.value) {
            PlayMode.SEQUENTIAL -> PlayMode.REPEAT_ONE
            PlayMode.REPEAT_ONE -> PlayMode.SHUFFLE
            PlayMode.SHUFFLE -> PlayMode.SEQUENTIAL
        }
    }

    fun updatePosition() {
        if (player.contentDuration > 0) {
            _currentPosition.value = player.currentPosition
            _duration.value = player.contentDuration
        }
    }

    fun release() {
        player.removeListener(listener)
        player.release()
    }
}
