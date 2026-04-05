<template>
  <DashboardLayout>
    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-3">
      <div>
        <h2 class="fw-bold text-dark m-0">Teams</h2>
        <p class="text-muted small m-0 mt-1">Create teams, invite members, and manage roles.</p>
      </div>
      <AppButton variant="primary" class="rounded-pill px-4" @click="showCreateModal = true">+ Create Team</AppButton>
    </div>

    <div class="row g-4 mb-4">
      <div class="col-md-4">
        <StatCard label="Teams" :value="teams.length" />
      </div>
      <div class="col-md-4">
        <StatCard label="Pending Invites" :value="pendingInvites.length" />
      </div>
      <div class="col-md-4">
        <StatCard label="Selected Team Members" :value="selectedTeamMembers.length" />
      </div>
    </div>

    <div v-if="pageError" class="alert alert-danger rounded-4 border-0 shadow-sm">{{ pageError }}</div>

    <div class="card border-0 shadow-sm rounded-4 p-4 mb-4">
      <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
        <h5 class="fw-bold m-0">My Pending Invites</h5>
        <AppBadge variant="warning">{{ pendingInvites.length }}</AppBadge>
      </div>
      <div class="d-flex flex-column gap-3">
        <div v-for="invite in pendingInvites" :key="invite.id" class="d-flex justify-content-between align-items-center border rounded-4 p-3 flex-wrap gap-3">
          <div>
            <h6 class="m-0 fw-bold">{{ invite.teamName }}</h6>
            <p class="m-0 text-muted small">Invited by {{ invite.invitedByUsername }}</p>
          </div>
          <div class="d-flex gap-2">
            <AppButton variant="success" @click="acceptPendingInvite(invite.id)">Accept</AppButton>
            <AppButton variant="light" @click="declinePendingInvite(invite.id)">Decline</AppButton>
          </div>
        </div>
        <div v-if="pendingInvites.length === 0" class="text-muted small">No pending invitations.</div>
      </div>
    </div>

    <div class="row g-4">
      <div class="col-lg-4">
        <div class="card border-0 shadow-sm rounded-4 p-4 h-100">
          <h5 class="fw-bold mb-3">Your Teams</h5>
          <div class="d-flex flex-column gap-3">
            <button
              v-for="team in teams"
              :key="team.id"
              type="button"
              class="btn text-start border rounded-4 p-3"
              :class="selectedTeam?.id === team.id ? 'btn-dark' : 'btn-light'"
              @click="selectTeam(team)"
            >
              <div class="d-flex justify-content-between align-items-center mb-2">
                <span class="fw-bold">{{ team.name }}</span>
                <AppBadge :variant="team.currentUserRole === 'OWNER' ? 'dark' : team.currentUserRole === 'MANAGER' ? 'primary' : 'secondary'">
                  {{ team.currentUserRole }}
                </AppBadge>
              </div>
              <div class="small opacity-75">{{ team.description || 'No description provided.' }}</div>
            </button>
            <div v-if="teams.length === 0" class="text-muted small">Create a team to begin.</div>
          </div>
        </div>
      </div>

      <div class="col-lg-8" v-if="selectedTeam">
        <div class="row g-4">
          <div class="col-12">
            <div class="card border-0 shadow-sm rounded-4 p-4">
              <div class="d-flex justify-content-between align-items-center mb-3">
                <h5 class="fw-bold m-0">Team Details</h5>
                <AppButton variant="light" @click="saveTeamDetails">Save</AppButton>
              </div>
              <div class="row g-3">
                <div class="col-md-6">
                  <FormGroup label="Team Name" class="mb-0">
                    <AppInput v-model="editableTeam.name" />
                  </FormGroup>
                </div>
                <div class="col-md-6">
                  <FormGroup label="Description" class="mb-0">
                    <AppInput v-model="editableTeam.description" />
                  </FormGroup>
                </div>
              </div>
            </div>
          </div>
          <div class="col-xl-6">
            <TeamMembersPanel
              :members="selectedTeamMembers"
              :can-manage="selectedTeam.currentUserRole === 'OWNER'"
              @role-change="changeMemberRole"
              @remove="removeTeamMember"
            />
          </div>
          <div v-if="selectedTeam.currentUserRole !== 'MEMBER'" class="col-xl-6">
            <TeamInvitesPanel
              :invites="selectedTeamInvites"
              :can-invite="selectedTeam.currentUserRole !== 'MEMBER'"
              @invite="sendInvite"
            />
          </div>
        </div>
      </div>
    </div>

    <div v-if="showCreateModal" class="modal-backdrop fade show" style="display: block;"></div>
    <div v-if="showCreateModal" class="modal fade show" tabindex="-1" style="display: block;">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow-lg rounded-4">
          <div class="modal-header border-0 pb-0">
            <h5 class="fw-bold text-dark">Create Team</h5>
            <button type="button" class="btn-close" @click="showCreateModal = false"></button>
          </div>
          <div class="modal-body py-4">
            <FormGroup label="Name">
              <AppInput v-model="newTeam.name" placeholder="Team name" />
            </FormGroup>
            <FormGroup label="Description" class="mb-0">
              <AppInput v-model="newTeam.description" placeholder="Short description" />
            </FormGroup>
          </div>
          <div class="modal-footer border-0 pt-0">
            <AppButton variant="light" @click="showCreateModal = false">Cancel</AppButton>
            <AppButton variant="primary" @click="createNewTeam" :disabled="!newTeam.name.trim()">Create</AppButton>
          </div>
        </div>
      </div>
    </div>
  </DashboardLayout>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import DashboardLayout from '../components/templates/DashboardLayout.vue'
import AppButton from '../components/atoms/AppButton.vue'
import AppInput from '../components/atoms/AppInput.vue'
import AppBadge from '../components/atoms/AppBadge.vue'
import FormGroup from '../components/molecules/FormGroup.vue'
import StatCard from '../components/molecules/StatCard.vue'
import TeamMembersPanel from '../components/organisms/TeamMembersPanel.vue'
import TeamInvitesPanel from '../components/organisms/TeamInvitesPanel.vue'
import {
  acceptInvite,
  createInvite,
  createTeam,
  declineInvite,
  getMyInvites,
  getTeamInvites,
  getTeamMembers,
  getTeams,
  removeMember,
  updateMemberRole,
  updateTeam
} from '../services/teamService'

const teams = ref([])
const pendingInvites = ref([])
const selectedTeam = ref(null)
const selectedTeamMembers = ref([])
const selectedTeamInvites = ref([])
const editableTeam = ref({ name: '', description: '' })
const newTeam = ref({ name: '', description: '' })
const showCreateModal = ref(false)
const pageError = ref('')

const loadTeams = async () => {
  teams.value = await getTeams()
  if (!selectedTeam.value && teams.value.length > 0) {
    await selectTeam(teams.value[0])
  } else if (selectedTeam.value) {
    const refreshed = teams.value.find((team) => team.id === selectedTeam.value.id)
    if (refreshed) {
      selectedTeam.value = refreshed
      editableTeam.value = { name: refreshed.name, description: refreshed.description || '' }
    }
  }
}

const loadPendingInvites = async () => {
  pendingInvites.value = await getMyInvites()
}

const selectTeam = async (team) => {
  selectedTeam.value = team
  editableTeam.value = { name: team.name, description: team.description || '' }
  selectedTeamMembers.value = await getTeamMembers(team.id)
  selectedTeamInvites.value = await getTeamInvites(team.id)
}

const createNewTeam = async () => {
  await createTeam(newTeam.value)
  newTeam.value = { name: '', description: '' }
  showCreateModal.value = false
  await loadTeams()
}

const saveTeamDetails = async () => {
  if (!selectedTeam.value) return
  await updateTeam(selectedTeam.value.id, editableTeam.value)
  await loadTeams()
}

const sendInvite = async (username) => {
  if (!selectedTeam.value || !username) return
  await createInvite(selectedTeam.value.id, username)
  selectedTeamInvites.value = await getTeamInvites(selectedTeam.value.id)
  await loadTeams()
}

const changeMemberRole = async ({ member, role }) => {
  await updateMemberRole(selectedTeam.value.id, member.userId, role)
  selectedTeamMembers.value = await getTeamMembers(selectedTeam.value.id)
}

const removeTeamMember = async (member) => {
  await removeMember(selectedTeam.value.id, member.userId)
  selectedTeamMembers.value = await getTeamMembers(selectedTeam.value.id)
}

const acceptPendingInvite = async (inviteId) => {
  await acceptInvite(inviteId)
  await Promise.all([loadTeams(), loadPendingInvites()])
}

const declinePendingInvite = async (inviteId) => {
  await declineInvite(inviteId)
  await loadPendingInvites()
}

onMounted(async () => {
  try {
    await Promise.all([loadTeams(), loadPendingInvites()])
  } catch (error) {
    pageError.value = error.response?.data?.message || 'Could not load teams.'
  }
})
</script>
