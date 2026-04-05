import axios from 'axios'

export const getDashboardStats = async () => (await axios.get('/api/dashboard/stats')).data
export const getOverview = async () => (await axios.get('/api/dashboard/overview')).data
export const getTeamDashboard = async (teamId) => (await axios.get(`/api/dashboard/team/${teamId}`)).data
