import { request } from './index'
import type { LoginRequest, LoginResponse, User } from '@/types'

export const authApi = {
  login(data: LoginRequest) {
    return request.post<LoginResponse>('/admin/auth/login', data)
  },

  logout() {
    return request.post<void>('/admin/auth/logout')
  },

  getCurrentUser() {
    return request.get<User>('/admin/auth/info')
  },

  refreshToken() {
    return request.post<{ token: string }>('/admin/auth/refresh')
  }
}
