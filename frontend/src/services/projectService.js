import axios from 'axios'
import { unwrapCollectionResponse, withCollectionDefaults } from './apiHelpers'

export const getProjects = async (params = {}) => unwrapCollectionResponse((await axios.get('/api/projects', { params: withCollectionDefaults(params) })).data)
export const createProject = async (payload) => (await axios.post('/api/projects', payload)).data
