<template>
  <div class="playlist-list">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>歌单列表</span>
          <div class="header-actions">
            <el-input
              v-model="searchKeyword"
              placeholder="搜索歌单名称"
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
            <el-button type="primary" @click="handleCreate">
              <el-icon><Plus /></el-icon>
              新建歌单
            </el-button>
          </div>
        </div>
      </template>

      <el-table v-loading="loading" :data="playlistList" style="width: 100%">
        <el-table-column prop="coverUrl" label="封面" width="80">
          <template #default="{ row }">
            <el-image
              :src="row.coverUrl || defaultCover"
              style="width: 50px; height: 50px; border-radius: 4px;"
              fit="cover"
            >
              <template #error>
                <div class="image-placeholder">
                  <el-icon><Picture /></el-icon>
                </div>
              </template>
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="歌单名称" min-width="150" show-overflow-tooltip />
        <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip />
        <el-table-column prop="musicCount" label="歌曲数" width="80" />
        <el-table-column prop="playCount" label="播放量" width="80" />
        <el-table-column prop="isPublic" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.isPublic ? 'success' : 'info'">
              {{ row.isPublic ? '公开' : '私密' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="创建时间" width="160">
          <template #default="{ row }">
            {{ formatDate(row.createdAt) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
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
          @size-change="fetchPlaylistList"
          @current-change="fetchPlaylistList"
        />
      </div>
    </el-card>

    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑歌单' : '新建歌单'"
      width="500px"
      @closed="resetForm"
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入歌单名称" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="3"
            placeholder="请输入歌单描述"
          />
        </el-form-item>
        <el-form-item label="封面" prop="coverUrl">
          <el-input v-model="form.coverUrl" placeholder="请输入封面URL" />
        </el-form-item>
        <el-form-item label="状态" prop="isPublic">
          <el-switch
            v-model="form.isPublic"
            active-text="公开"
            inactive-text="私密"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, FormInstance, FormRules } from 'element-plus'
import { playlistApi } from '@/api/playlist'
import type { Playlist } from '@/types'

const loading = ref(false)
const submitLoading = ref(false)
const searchKeyword = ref('')
const playlistList = ref<Playlist[]>([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const editId = ref<number | null>(null)
const formRef = ref<FormInstance>()

const defaultCover = 'https://via.placeholder.com/50?text=Music'

const pagination = reactive({
  page: 1,
  size: 20,
  total: 0
})

const form = reactive({
  name: '',
  description: '',
  coverUrl: '',
  isPublic: true
})

const rules: FormRules = {
  name: [
    { required: true, message: '请输入歌单名称', trigger: 'blur' }
  ]
}

function formatDate(dateStr: string): string {
  return new Date(dateStr).toLocaleString('zh-CN')
}

async function fetchPlaylistList() {
  loading.value = true
  try {
    const res = await playlistApi.getList({
      page: pagination.page - 1,
      size: pagination.size,
      keyword: searchKeyword.value || undefined
    })
    playlistList.value = res.data.content
    pagination.total = res.data.totalElements
  } catch (error) {
    console.error('获取歌单列表失败:', error)
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  pagination.page = 1
  fetchPlaylistList()
}

function handleCreate() {
  isEdit.value = false
  editId.value = null
  dialogVisible.value = true
}

function handleEdit(row: Playlist) {
  isEdit.value = true
  editId.value = row.id
  form.name = row.name
  form.description = row.description
  form.coverUrl = row.coverUrl || ''
  form.isPublic = row.isPublic
  dialogVisible.value = true
}

async function handleDelete(row: Playlist) {
  try {
    await ElMessageBox.confirm(`确定要删除歌单 "${row.name}" 吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await playlistApi.delete(row.id)
    ElMessage.success('删除成功')
    fetchPlaylistList()
  } catch {
    // 用户取消
  }
}

function resetForm() {
  form.name = ''
  form.description = ''
  form.coverUrl = ''
  form.isPublic = true
  formRef.value?.resetFields()
}

async function handleSubmit() {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    submitLoading.value = true
    try {
      if (isEdit.value && editId.value) {
        await playlistApi.update(editId.value, {
          name: form.name,
          description: form.description,
          coverUrl: form.coverUrl || undefined,
          isPublic: form.isPublic
        })
        ElMessage.success('更新成功')
      } else {
        await playlistApi.create({
          name: form.name,
          description: form.description,
          coverUrl: form.coverUrl || undefined,
          isPublic: form.isPublic
        })
        ElMessage.success('创建成功')
      }
      dialogVisible.value = false
      fetchPlaylistList()
    } catch (error) {
      console.error('操作失败:', error)
    } finally {
      submitLoading.value = false
    }
  })
}

onMounted(() => {
  fetchPlaylistList()
})
</script>

<style scoped>
.playlist-list {
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

.image-placeholder {
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f7fa;
  border-radius: 4px;
  color: #909399;
}
</style>
