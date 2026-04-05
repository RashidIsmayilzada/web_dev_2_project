import axios from 'axios'

export const getTasks = async (params = {}) => (await axios.get('/api/tasks', { params })).data
export const getTasksForProject = async (projectId) => (await axios.get(`/api/tasks/project/${projectId}`)).data
export const createTask = async (payload) => (await axios.post('/api/tasks', payload)).data
export const updateTask = async (taskId, payload) => (await axios.put(`/api/tasks/${taskId}`, payload)).data
export const deleteTask = async (taskId) => axios.delete(`/api/tasks/${taskId}`)
