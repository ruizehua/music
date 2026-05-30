import { request } from './index'
import type { Playlist, PlaylistCreateRequest, PlaylistUpdateRequest, PageResult } from '@/types'

export const playlistApi = {
  getList(params: { page: number; size: number; keyword?: string }) {
    return request.get<PageResult<Playlist>>('/playlists', { params })
  },

  getById(id: number) {
    return request.get<Playlist>(`/playlists/${id}`)
  },

  create(data: PlaylistCreateRequest) {
    return request.post<Playlist>('/playlists', data)
  },

  update(id: number, data: PlaylistUpdateRequest) {
    return request.put<Playlist>(`/playlists/${id}`, data)
  },

  delete(id: number) {
    return request.delete<void>(`/playlists/${id}`)
  },

  addMusic(playlistId: number, musicId: number) {
    return request.post<void>(`/playlists/${playlistId}/music/${musicId}`)
  },

  removeMusic(playlistId: number, musicId: number) {
    return request.delete<void>(`/playlists/${playlistId}/music/${musicId}`)
  },

  getMusicList(playlistId: number) {
    return request.get<{ content: import('@/types').Music[] }>(`/playlists/${playlistId}/music`)
  }
}
