import axios from 'axios'
import { unwrapCollectionResponse, withCollectionDefaults } from './apiHelpers'

export const getTimeLogs = async (params = {}) => unwrapCollectionResponse((await axios.get('/api/timelogs', { params: withCollectionDefaults(params) })).data)
export const createTimeLog = async (payload) => (await axios.post('/api/timelogs', payload)).data
