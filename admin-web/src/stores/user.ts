import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { User } from '@/types'
import { authApi } from '@/api/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref<string | null>(localStorage.getItem('token'))
  const userInfo = ref<User | null>(null)

  const isLoggedIn = computed(() => !!token.value)
  const username = computed(() => userInfo.value?.username || '')
  const nickname = computed(() => userInfo.value?.nickname || userInfo.value?.username || '')
  const avatar = computed(() => userInfo.value?.avatar || '')
  const role = computed(() => userInfo.value?.role || '')

  function setToken(newToken: string) {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  function setUser(user: User) {
    userInfo.value = user
    localStorage.setItem('user', JSON.stringify(user))
  }

  function loadUserFromStorage() {
    const storedUser = localStorage.getItem('user')
    if (storedUser) {
      try {
        userInfo.value = JSON.parse(storedUser)
      } catch {
        userInfo.value = null
      }
    }
  }

  async function login(username: string, password: string) {
    const res = await authApi.login({ username, password })
    setToken(res.data.token)
    const userInfo = res.data.userInfo || res.data.user
    setUser({
      id: 0,
      username: userInfo.username || '',
      nickname: userInfo.username || '',
      avatar: '',
      role: userInfo.role || ''
    })
    return res
  }

  async function logout() {
    try {
      await authApi.logout()
    } finally {
      token.value = null
      userInfo.value = null
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    }
  }

  async function fetchCurrentUser() {
    if (!token.value) return null
    try {
      const res = await authApi.getCurrentUser()
      setUser(res.data)
      return res.data
    } catch {
      logout()
      return null
    }
  }

  loadUserFromStorage()

  return {
    token,
    userInfo,
    isLoggedIn,
    username,
    nickname,
    avatar,
    role,
    setToken,
    setUser,
    login,
    logout,
    fetchCurrentUser
  }
})
