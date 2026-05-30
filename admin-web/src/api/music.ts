import { request } from './index'
import type { Music, MusicListParams, PageResult } from '@/types'

export const musicApi = {
  getList(params: MusicListParams) {
    return request.get<PageResult<Music>>('/music', { params })
  },

  getById(id: number) {
    return request.get<Music>(`/music/${id}`)
  },

  delete(id: number) {
    return request.delete<void>(`/music/${id}`)
  },

  batchDelete(ids: number[]) {
    return request.post<void>('/music/batch-delete', { ids })
  },

  scan() {
    return request.post<{ message: string }>('/music/scan')
  },

  update(id: number, data: Partial<Music>) {
    return request.put<Music>(`/music/${id}`, data)
  },

  getArtists() {
    return request.get<string[]>('/music/artists')
  },

  getAlbums() {
    return request.get<string[]>('/music/albums')
  }
}
