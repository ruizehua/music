<template>
  <div class="music-list">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>音乐列表</span>
          <div class="header-actions">
            <el-input
              v-model="searchKeyword"
              placeholder="搜索歌曲、歌手、专辑"
              style="width: 250px;"
              clearable
              @keyup.enter="handleSearch"
            >
              <template #prefix>
                <el-icon><Search /></el-icon>
              </template>
            </el-input>
            <el-button type="primary" @click="handleSearch">
              <el-icon><Search /></el-icon>
              搜索
            </el-button>
            <el-button type="success" @click="handleScan">
              <el-icon><Refresh /></el-icon>
              扫描音乐
            </el-button>
          </div>
        </div>
      </template>

      <el-table
        v-loading="loading"
        :data="musicList"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="50" />
        <el-table-column prop="title" label="歌曲名" min-width="150" show-overflow-tooltip />
        <el-table-column prop="artist" label="歌手" width="120" show-overflow-tooltip />
        <el-table-column prop="album" label="专辑" width="120" show-overflow-tooltip />
        <el-table-column prop="duration" label="时长" width="80">
          <template #default="{ row }">
            {{ formatDuration(row.duration) }}
          </template>
        </el-table-column>
        <el-table-column prop="format" label="格式" width="80" />
        <el-table-column prop="fileSize" label="大小" width="100">
          <template #default="{ row }">
            {{ formatSize(row.fileSize) }}
          </template>
        </el-table-column>
        <el-table-column prop="playCount" label="播放量" width="80" />
        <el-table-column prop="createdAt" label="添加时间" width="160">
          <template #default="{ row }">
            {{ formatDate(row.createdAt) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button type="danger" link @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.size"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="fetchMusicList"
          @current-change="fetchMusicList"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { musicApi } from '@/api/music'
import type { Music } from '@/types'

const loading = ref(false)
const searchKeyword = ref('')
const musicList = ref<Music[]>([])
const selectedRows = ref<Music[]>([])

const pagination = reactive({
  page: 1,
  size: 20,
  total: 0
})

function formatDuration(seconds: number): string {
  const minutes = Math.floor(seconds / 60)
  const secs = seconds % 60
  return `${minutes}:${secs.toString().padStart(2, '0')}`
}

function formatSize(bytes: number): string {
  if (bytes === 0) return '0 B'
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i]
}

function formatDate(dateStr: string): string {
  return new Date(dateStr).toLocaleString('zh-CN')
}

async function fetchMusicList() {
  loading.value = true
  try {
    const res = await musicApi.getList({
      page: pagination.page - 1,
      size: pagination.size,
      keyword: searchKeyword.value || undefined
    })
    musicList.value = res.data.content
    pagination.total = res.data.totalElements
  } catch (error) {
    console.error('获取音乐列表失败:', error)
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  pagination.page = 1
  fetchMusicList()
}

async function handleScan() {
  try {
    await ElMessageBox.confirm('确定要扫描音乐库吗？这可能需要一些时间。', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'info'
    })
    const res = await musicApi.scan()
    ElMessage.success(res.data.message || '扫描完成')
    fetchMusicList()
  } catch {
    // 用户取消
  }
}

async function handleDelete(row: Music) {
  try {
    await ElMessageBox.confirm(`确定要删除歌曲 "${row.title}" 吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await musicApi.delete(row.id)
    ElMessage.success('删除成功')
    fetchMusicList()
  } catch {
    // 用户取消
  }
}

function handleSelectionChange(rows: Music[]) {
  selectedRows.value = rows
}

onMounted(() => {
  fetchMusicList()
})
</script>

<style scoped>
.music-list {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
