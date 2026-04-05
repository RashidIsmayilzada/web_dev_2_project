import axios from 'axios'

export const getWeeklySummary = async () => (await axios.get('/api/reports/weekly-summary')).data
export const getTeamHours = async (teamId) => (await axios.get('/api/reports/team-hours', { params: { teamId } })).data
export const getCompletedTasksReport = async (teamId) => (await axios.get('/api/reports/completed-tasks', { params: { teamId } })).data
export const getTrends = async (teamId) => (await axios.get('/api/reports/trends', { params: { teamId } })).data
