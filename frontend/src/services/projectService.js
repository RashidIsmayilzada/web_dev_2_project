import axios from 'axios'

export const getProjects = async () => (await axios.get('/api/projects')).data
export const createProject = async (payload) => (await axios.post('/api/projects', payload)).data
