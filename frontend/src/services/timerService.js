import axios from 'axios'

export const getActiveTimer = async () => (await axios.get('/api/timers/active')).data
export const startTimer = async (payload) => (await axios.post('/api/timers/start', payload)).data
export const stopTimer = async (timerId, payload = {}) => (await axios.post(`/api/timers/${timerId}/stop`, payload)).data
export const cancelTimer = async (timerId) => axios.post(`/api/timers/${timerId}/cancel`)
