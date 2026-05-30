export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
}

export interface LoginRequest {
  username: string
  password: string
}

export interface LoginResponse {
  token: string
  userInfo: {
    username: string
    role: string
  }
  user: User
}

export interface User {
  id: number
  username: string
  nickname: string
  avatar: string
  role: string
}

export interface Music {
  id: number
  title: string
  artist: string
  album: string
  duration: number
  filePath: string
  coverUrl: string
  fileSize: number
  format: string
  bitRate: number
  sampleRate: number
  playCount: number
  createdAt: string
  updatedAt: string
}

export interface MusicListParams {
  page: number
  size: number
  keyword?: string
  artist?: string
  album?: string
}

export interface PageResult<T> {
  content: T[]
  totalElements: number
  totalPages: number
  size: number
  number: number
}

export interface Playlist {
  id: number
  name: string
  description: string
  coverUrl: string
  musicCount: number
  playCount: number
  isPublic: boolean
  createdAt: string
  updatedAt: string
}

export interface PlaylistCreateRequest {
  name: string
  description: string
  coverUrl?: string
  isPublic: boolean
}

export interface PlaylistUpdateRequest {
  name?: string
  description?: string
  coverUrl?: string
  isPublic?: boolean
}

export interface Stats {
  totalMusic: number
  totalPlaylist: number
  totalUser: number
  totalStorage: number
  todayPlayCount: number
  storageUsed: number
}

export interface SystemConfig {
  id: number
  key: string
  value: string
  description: string
  updatedAt: string
}

export interface ConfigUpdateRequest {
  key: string
  value: string
}
