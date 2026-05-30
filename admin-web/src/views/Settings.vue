<template>
  <div class="settings">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>系统设置</span>
        </div>
      </template>

      <el-tabs v-model="activeTab">
        <el-tab-pane label="基础配置" name="basic">
          <el-form v-loading="loading" label-width="120px" style="max-width: 600px;">
            <el-form-item label="音乐存储路径">
              <el-input v-model="configs.musicPath" placeholder="/data/music" />
            </el-form-item>
            <el-form-item label="最大上传大小">
              <el-input v-model="configs.maxUploadSize" placeholder="100MB" />
            </el-form-item>
            <el-form-item label="允许的格式">
              <el-input v-model="configs.allowedFormats" placeholder="mp3,flac,wav,aac" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSaveBasic">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="播放设置" name="playback">
          <el-form label-width="120px" style="max-width: 600px;">
            <el-form-item label="默认音量">
              <el-slider v-model="configs.defaultVolume" :max="100" show-input />
            </el-form-item>
            <el-form-item label="自动播放">
              <el-switch v-model="configs.autoPlay" />
            </el-form-item>
            <el-form-item label="随机播放">
              <el-switch v-model="configs.shuffle" />
            </el-form-item>
            <el-form-item label="循环模式">
              <el-select v-model="configs.repeatMode" style="width: 200px;">
                <el-option label="不循环" value="none" />
                <el-option label="单曲循环" value="single" />
                <el-option label="列表循环" value="all" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSavePlayback">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="系统信息" name="system">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="系统版本">1.0.0</el-descriptions-item>
            <el-descriptions-item label="后端版本">1.0.0</el-descriptions-item>
            <el-descriptions-item label="Vue 版本">3.4.21</el-descriptions-item>
            <el-descriptions-item label="Element Plus">2.6.1</el-descriptions-item>
            <el-descriptions-item label="API 地址">http://localhost:8080/api/v1</el-descriptions-item>
            <el-descriptions-item label="运行环境">Development</el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { configApi } from '@/api/stats'

const loading = ref(false)
const activeTab = ref('basic')

const configs = reactive({
  musicPath: '',
  maxUploadSize: '',
  allowedFormats: '',
  defaultVolume: 80,
  autoPlay: false,
  shuffle: false,
  repeatMode: 'none'
})

async function fetchConfigs() {
  loading.value = true
  try {
    const res = await configApi.getAll()
    res.data.forEach((config) => {
      switch (config.key) {
        case 'musicPath':
          configs.musicPath = config.value
          break
        case 'maxUploadSize':
          configs.maxUploadSize = config.value
          break
        case 'allowedFormats':
          configs.allowedFormats = config.value
          break
        case 'defaultVolume':
          configs.defaultVolume = parseInt(config.value) || 80
          break
        case 'autoPlay':
          configs.autoPlay = config.value === 'true'
          break
        case 'shuffle':
          configs.shuffle = config.value === 'true'
          break
        case 'repeatMode':
          configs.repeatMode = config.value
          break
      }
    })
  } catch (error) {
    console.error('获取配置失败:', error)
  } finally {
    loading.value = false
  }
}

async function handleSaveBasic() {
  try {
    await Promise.all([
      configApi.update({ key: 'musicPath', value: configs.musicPath }),
      configApi.update({ key: 'maxUploadSize', value: configs.maxUploadSize }),
      configApi.update({ key: 'allowedFormats', value: configs.allowedFormats })
    ])
    ElMessage.success('保存成功')
  } catch (error) {
    console.error('保存配置失败:', error)
  }
}

async function handleSavePlayback() {
  try {
    await Promise.all([
      configApi.update({ key: 'defaultVolume', value: configs.defaultVolume.toString() }),
      configApi.update({ key: 'autoPlay', value: configs.autoPlay.toString() }),
      configApi.update({ key: 'shuffle', value: configs.shuffle.toString() }),
      configApi.update({ key: 'repeatMode', value: configs.repeatMode })
    ])
    ElMessage.success('保存成功')
  } catch (error) {
    console.error('保存配置失败:', error)
  }
}

onMounted(() => {
  fetchConfigs()
})
</script>

<style scoped>
.settings {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
