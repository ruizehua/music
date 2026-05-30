import { request } from './index'
import type { Stats, SystemConfig, ConfigUpdateRequest } from '@/types'

export const statsApi = {
  getOverview() {
    return request.get<Stats>('/stats/overview')
  },

  getPlayTrend(days: number = 7) {
    return request.get<{ date: string; count: number }[]>('/stats/play-trend', { params: { days } })
  },

  getPopularMusic(limit: number = 10) {
    return request.get<{ id: number; title: string; artist: string; playCount: number }[]>('/stats/popular-music', { params: { limit } })
  }
}

export const configApi = {
  getAll() {
    return request.get<SystemConfig[]>('/config')
  },

  getByKey(key: string) {
    return request.get<SystemConfig>(`/config/${key}`)
  },

  update(data: ConfigUpdateRequest) {
    return request.put<SystemConfig>('/config', data)
  }
}
