import axios from 'axios'
import { unwrapCollectionResponse, withCollectionDefaults } from './apiHelpers'

export const getTasks = async (params = {}) => unwrapCollectionResponse((await axios.get('/api/tasks', { params: withCollectionDefaults(params) })).data)
export const getTasksForProject = async (projectId, params = {}) => unwrapCollectionResponse((await axios.get(`/api/tasks/project/${projectId}`, { params: withCollectionDefaults(params) })).data)
export const createTask = async (payload) => (await axios.post('/api/tasks', payload)).data
export const updateTask = async (taskId, payload) => (await axios.put(`/api/tasks/${taskId}`, payload)).data
export const deleteTask = async (taskId) => axios.delete(`/api/tasks/${taskId}`)
