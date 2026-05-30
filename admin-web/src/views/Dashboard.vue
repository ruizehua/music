<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon music">
              <el-icon :size="32"><Headset /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.totalMusic }}</div>
              <div class="stat-label">音乐总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon playlist">
              <el-icon :size="32"><List /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.totalPlaylist }}</div>
              <div class="stat-label">歌单总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon user">
              <el-icon :size="32"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.totalUser }}</div>
              <div class="stat-label">用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon storage">
              <el-icon :size="32"><Folder /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ formatSize(stats.storageUsed) }}</div>
              <div class="stat-label">存储使用</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>播放趋势</span>
            </div>
          </template>
          <div class="chart-placeholder" v-if="playTrend.length">
            <el-table :data="playTrend" style="width: 100%">
              <el-table-column prop="date" label="日期" />
              <el-table-column prop="count" label="播放次数" />
            </el-table>
          </div>
          <el-empty v-else description="暂无数据" />
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>热门音乐</span>
            </div>
          </template>
          <el-table :data="popularMusic" style="width: 100%" max-height="300">
            <el-table-column prop="title" label="歌曲" show-overflow-tooltip />
            <el-table-column prop="artist" label="歌手" width="100" show-overflow-tooltip />
            <el-table-column prop="playCount" label="播放量" width="80" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { statsApi } from '@/api/stats'
import type { Stats } from '@/types'

const stats = ref<Stats>({
  totalMusic: 0,
  totalPlaylist: 0,
  totalUser: 0,
  totalStorage: 0,
  todayPlayCount: 0,
  storageUsed: 0
})

const playTrend = ref<{ date: string; count: number }[]>([])
const popularMusic = ref<{ id: number; title: string; artist: string; playCount: number }[]>([])

function formatSize(bytes: number): string {
  if (bytes === 0) return '0 B'
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB', 'TB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i]
}

async function fetchStats() {
  try {
    const res = await statsApi.getOverview()
    stats.value = res.data
  } catch (error) {
    console.error('获取统计数据失败:', error)
  }
}

async function fetchPlayTrend() {
  try {
    const res = await statsApi.getPlayTrend(7)
    playTrend.value = res.data
  } catch (error) {
    console.error('获取播放趋势失败:', error)
  }
}

async function fetchPopularMusic() {
  try {
    const res = await statsApi.getPopularMusic(10)
    popularMusic.value = res.data
  } catch (error) {
    console.error('获取热门音乐失败:', error)
  }
}

onMounted(() => {
  fetchStats()
  fetchPlayTrend()
  fetchPopularMusic()
})
</script>

<style scoped>
.dashboard {
  padding: 0;
}

.stat-card {
  border-radius: 8px;
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.stat-icon.music {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.stat-icon.playlist {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.stat-icon.user {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-icon.storage {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

.stat-label {
  font-size: 14px;
  color: #999;
  margin-top: 4px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chart-placeholder {
  height: 300px;
  overflow: auto;
}
</style>
