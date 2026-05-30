import { request } from './index'
import type { Stats, SystemConfig, ConfigUpdateRequest } from '@/types'

export const statsApi = {
  getOverview() {
    return request.get<Stats>('/admin/stats/overview')
  },

  getPlayTrend(days: number = 7) {
    return request.get<{ date: string; count: number }[]>('/admin/stats/play-trend', { params: { days } })
  },

  getPopularMusic(limit: number = 10) {
    return request.get<{ id: number; title: string; artist: string; playCount: number }[]>('/admin/stats/popular-music', { params: { limit } })
  }
}

export const configApi = {
  getAll() {
    return request.get<SystemConfig[]>('/admin/config')
  },

  getByKey(key: string) {
    return request.get<SystemConfig>(`/admin/config/${key}`)
  },

  update(data: ConfigUpdateRequest) {
    return request.put<SystemConfig>('/admin/config', data)
  }
}
