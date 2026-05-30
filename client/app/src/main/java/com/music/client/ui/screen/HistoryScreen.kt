package com.music.client.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.music.client.viewmodel.HistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateToPlayer: () -> Unit,
    historyViewModel: HistoryViewModel = hiltViewModel()
) {
    val historyList by historyViewModel.historyList.collectAsState()
    val isLoading by historyViewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("播放历史") },
                actions = {
                    if (historyList.isNotEmpty()) {
                        IconButton(onClick = { historyViewModel.clearHistory() }) {
                            Icon(Icons.Default.Delete, "清空历史")
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
        } else if (historyList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("暂无播放历史", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(historyList) { history ->
                    ListItem(
                        headlineContent = {
                            Text(history.title ?: history.fileName ?: "未知歌曲")
                        },
                        supportingContent = {
                            Text(history.artist ?: "未知艺术家")
                        }
                    )
                }
            }
        }
    }
}
