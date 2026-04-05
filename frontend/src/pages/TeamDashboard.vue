<template>
  <DashboardLayout>
    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-3">
      <div>
        <h2 class="fw-bold text-dark m-0">Team Dashboard</h2>
        <p class="text-muted small m-0 mt-1">Choose a team to inspect productivity and member hours.</p>
      </div>
      <AppSelect v-model="selectedTeamId" class="w-auto" @update:model-value="loadDashboard">
        <option value="">Select a team</option>
        <option v-for="team in teams" :key="team.id" :value="String(team.id)">{{ team.name }}</option>
      </AppSelect>
    </div>

    <div v-if="pageError" class="alert alert-danger rounded-4 border-0 shadow-sm">{{ pageError }}</div>

    <TeamDashboardSection :dashboard="dashboard" />
  </DashboardLayout>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import DashboardLayout from '../components/templates/DashboardLayout.vue'
import AppSelect from '../components/atoms/AppSelect.vue'
import TeamDashboardSection from '../components/organisms/TeamDashboardSection.vue'
import { getTeamDashboard } from '../services/dashboardService'
import { getTeams } from '../services/teamService'

const teams = ref([])
const selectedTeamId = ref('')
const dashboard = ref({ memberHours: [] })
const pageError = ref('')

const loadDashboard = async () => {
  if (!selectedTeamId.value) return
  dashboard.value = await getTeamDashboard(Number(selectedTeamId.value))
}

onMounted(async () => {
  try {
    teams.value = await getTeams()
    selectedTeamId.value = teams.value[0] ? String(teams.value[0].id) : ''
    await loadDashboard()
  } catch (error) {
    pageError.value = error.response?.data?.message || 'Could not load the team dashboard.'
  }
})
</script>
