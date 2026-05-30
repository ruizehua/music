package com.music.client.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.music.client.data.model.PlayMode
import com.music.client.data.model.PlaybackState
import com.music.client.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    onNavigateBack: () -> Unit,
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val currentMusic by playerViewModel.currentMusic.collectAsState()
    val playbackState by playerViewModel.playbackState.collectAsState()
    val currentPosition by playerViewModel.currentPosition.collectAsState()
    val duration by playerViewModel.duration.collectAsState()
    val playMode by playerViewModel.playMode.collectAsState()
    val isFavorite by playerViewModel.isFavorite.collectAsState()

    val isPlaying = playbackState == PlaybackState.PLAYING

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "返回")
                    }
                },
                title = { Text("正在播放") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 4.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.MusicNote,
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentMusic?.title ?: currentMusic?.fileName ?: "未选择歌曲",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = currentMusic?.artist ?: "未知艺术家",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (currentMusic?.album != null) {
                    Text(
                        text = currentMusic!!.album!!,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f,
                    onValueChange = { playerViewModel.seekTo((it * duration).toLong()) },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(formatDuration((currentPosition / 1000).toInt()))
                    Text(formatDuration((duration / 1000).toInt()))
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { playerViewModel.togglePlayMode() }) {
                    Icon(
                        when (playMode) {
                            PlayMode.SEQUENTIAL -> Icons.Default.Repeat
                            PlayMode.REPEAT_ONE -> Icons.Default.RepeatOne
                            PlayMode.SHUFFLE -> Icons.Default.Shuffle
                        },
                        contentDescription = "播放模式",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { playerViewModel.playPrevious() }) {
                    Icon(Icons.Default.SkipPrevious, "上一首", modifier = Modifier.size(36.dp))
                }
                FilledIconButton(
                    onClick = { playerViewModel.playOrPause() },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        if (isPlaying) "暂停" else "播放",
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(onClick = { playerViewModel.playNext() }) {
                    Icon(Icons.Default.SkipNext, "下一首", modifier = Modifier.size(36.dp))
                }
                IconButton(onClick = { playerViewModel.toggleFavorite() }) {
                    Icon(
                        if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        "收藏",
                        tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

private fun formatDuration(seconds: Int): String {
    if (seconds < 0) return "0:00"
    val min = seconds / 60
    val sec = seconds % 60
    return String.format("%d:%02d", min, sec)
}
