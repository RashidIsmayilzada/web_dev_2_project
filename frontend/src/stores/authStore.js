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
    setSession(data) {
      this.token = data.token
      this.user = { username: data.username }
      localStorage.setItem('token', data.token)
      localStorage.setItem('user', JSON.stringify(this.user))
      axios.defaults.headers.common['Authorization'] = `Bearer ${data.token}`
    },
    async login(credentials) {
      const payload = {
        username: credentials.username.trim(),
        password: credentials.password
      }

      const { data } = await axios.post('/api/auth/login', payload)
      this.setSession(data)
    },
    async register(details) {
      const payload = {
        username: details.username.trim(),
        email: details.email.trim(),
        password: details.password
      }

      const { data } = await axios.post('/api/auth/register', payload)
      this.setSession(data)
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
