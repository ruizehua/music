package com.music.client.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.music.client.viewmodel.PlaylistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPlayer: () -> Unit,
    backStackEntry: NavBackStackEntry? = null,
    playlistViewModel: PlaylistViewModel = hiltViewModel()
) {
    val playlistDetail by playlistViewModel.playlistDetail.collectAsState()
    val isLoading by playlistViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        val playlistId = backStackEntry?.arguments?.getLong("playlistId") ?: return@LaunchedEffect
        playlistViewModel.loadPlaylistDetail(playlistId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "返回")
                    }
                },
                title = { Text(playlistDetail?.name ?: "歌单详情") },
                actions = {
                    playlistDetail?.let { detail ->
                        IconButton(onClick = {
                            playlistViewModel.playPlaylist(detail)
                            onNavigateToPlayer()
                        }) {
                            Icon(Icons.Default.PlayArrow, "播放全部")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val songs = playlistDetail?.songs ?: emptyList()
            if (songs.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("歌单暂无歌曲", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(songs) { music ->
                        MusicListItem(
                            music = music,
                            onClick = {
                                playlistDetail?.let { detail ->
                                    playlistViewModel.playPlaylist(detail, songs.indexOf(music))
                                }
                                onNavigateToPlayer()
                            }
                        )
                    }
                }
            }
        }
    }
}
