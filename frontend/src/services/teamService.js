import axios from 'axios'
import { unwrapCollectionResponse, withCollectionDefaults } from './apiHelpers'

export const getTeams = async (params = {}) => unwrapCollectionResponse((await axios.get('/api/teams', { params: withCollectionDefaults(params) })).data)
export const createTeam = async (payload) => (await axios.post('/api/teams', payload)).data
export const updateTeam = async (teamId, payload) => (await axios.put(`/api/teams/${teamId}`, payload)).data
export const getTeamMembers = async (teamId, params = {}) => unwrapCollectionResponse((await axios.get(`/api/teams/${teamId}/members`, { params: withCollectionDefaults(params) })).data)
export const getTeamInvites = async (teamId, params = {}) => unwrapCollectionResponse((await axios.get(`/api/teams/${teamId}/invites`, { params: withCollectionDefaults(params) })).data)
export const getMyInvites = async (params = {}) => unwrapCollectionResponse((await axios.get('/api/teams/invites/me', { params: withCollectionDefaults(params) })).data)
export const createInvite = async (teamId, username) => (await axios.post(`/api/teams/${teamId}/invites`, { username })).data
export const acceptInvite = async (inviteId) => (await axios.post(`/api/teams/invites/${inviteId}/accept`)).data
export const declineInvite = async (inviteId) => (await axios.post(`/api/teams/invites/${inviteId}/decline`)).data
export const updateMemberRole = async (teamId, userId, role) => (await axios.put(`/api/teams/${teamId}/members/${userId}/role`, { role })).data
export const removeMember = async (teamId, userId) => axios.delete(`/api/teams/${teamId}/members/${userId}`)
