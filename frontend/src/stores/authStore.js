import { defineStore } from 'pinia'
import axios from 'axios'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    user: JSON.parse(localStorage.getItem('user')) || null
  }),
  getters: {
    isAuthenticated: (state) => !!state.token
  },
  actions: {
    async login(credentials) {
      const { data } = await axios.post('/api/auth/login', credentials)
      this.token = data.token
      this.user = { username: data.username }
      localStorage.setItem('token', data.token)
      localStorage.setItem('user', JSON.stringify(this.user))
      axios.defaults.headers.common['Authorization'] = `Bearer ${data.token}`
    },
    logout() {
      this.token = null
      this.user = null
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      delete axios.defaults.headers.common['Authorization']
    }
  }
})
