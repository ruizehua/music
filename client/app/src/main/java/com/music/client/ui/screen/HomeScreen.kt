package com.music.client.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.music.client.data.model.Music
import com.music.client.viewmodel.MusicViewModel
import com.music.client.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToLibrary: () -> Unit,
    onNavigateToPlayer: () -> Unit,
    onNavigateToPlaylists: () -> Unit,
    musicViewModel: MusicViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val musicList by musicViewModel.musicList.collectAsState()
    val currentMusic by playerViewModel.currentMusic.collectAsState()
    val isPlaying by playerViewModel.playbackState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("音乐") })
        },
        bottomBar = {
            if (currentMusic != null) {
                MiniPlayer(
                    music = currentMusic!!,
                    isPlaying = isPlaying == com.music.client.data.model.PlaybackState.PLAYING,
                    onPlayPause = { playerViewModel.playOrPause() },
                    onClick = onNavigateToPlayer
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            QuickActions(
                onLibraryClick = onNavigateToLibrary,
                onPlaylistsClick = onNavigateToPlaylists
            )

            Text(
                text = "最近添加",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            if (musicList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("暂无音乐", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn {
                    items(musicList.take(10)) { music ->
                        MusicListItem(
                            music = music,
                            onClick = {
                                musicViewModel.playMusic(music)
                                onNavigateToPlayer()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActions(
    onLibraryClick: () -> Unit,
    onPlaylistsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuickActionCard(
            icon = Icons.Default.LibraryMusic,
            label = "音乐库",
            onClick = onLibraryClick,
            modifier = Modifier.weight(1f)
        )
        QuickActionCard(
            icon = Icons.Default.QueueMusic,
            label = "歌单",
            onClick = onPlaylistsClick,
            modifier = Modifier.weight(1f)
        )
        QuickActionCard(
            icon = Icons.Default.Favorite,
            label = "收藏",
            onClick = onPlaylistsClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun QuickActionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = label, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun MiniPlayer(
    music: Music,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        tonalElevation = 3.dp,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = music.title ?: music.fileName,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
                Text(
                    text = music.artist ?: "未知艺术家",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }
            IconButton(onClick = onPlayPause) {
                Icon(
                    if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "暂停" else "播放"
                )
            }
        }
    }
}

@Composable
fun MusicListItem(
    music: Music,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = {
            Text(
                text = music.title ?: music.fileName,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        supportingContent = {
            Text(
                text = music.artist ?: "未知艺术家",
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
            )
        },
        trailingContent = {
            music.duration?.let { duration ->
                Text(
                    text = formatDuration(duration),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    )
}

private fun formatDuration(seconds: Int): String {
    val min = seconds / 60
    val sec = seconds % 60
    return String.format("%d:%02d", min, sec)
}
