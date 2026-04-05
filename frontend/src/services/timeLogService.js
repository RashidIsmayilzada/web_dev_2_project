import axios from 'axios'

export const getTimeLogs = async () => (await axios.get('/api/timelogs')).data
export const createTimeLog = async (payload) => (await axios.post('/api/timelogs', payload)).data
