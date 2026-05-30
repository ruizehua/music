import { request } from './index'
import type { LoginRequest, LoginResponse, User } from '@/types'

export const authApi = {
  login(data: LoginRequest) {
    return request.post<LoginResponse>('/auth/login', data)
  },

  logout() {
    return request.post<void>('/auth/logout')
  },

  getCurrentUser() {
    return request.get<User>('/auth/me')
  },

  refreshToken() {
    return request.post<{ token: string }>('/auth/refresh')
  }
}
