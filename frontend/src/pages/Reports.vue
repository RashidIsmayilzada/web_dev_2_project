<template>
  <DashboardLayout>
    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-3">
      <div>
        <h2 class="fw-bold text-dark m-0">Reports</h2>
        <p class="text-muted small m-0 mt-1">Weekly summaries, team hours, completed work, and trends.</p>
      </div>
      <AppSelect v-model="selectedTeamId" class="w-auto" @update:model-value="loadReports">
        <option value="">Select a team</option>
        <option v-for="team in teams" :key="team.id" :value="String(team.id)">{{ team.name }}</option>
      </AppSelect>
    </div>

    <div v-if="pageError" class="alert alert-danger rounded-4 border-0 shadow-sm">{{ pageError }}</div>

    <div class="row g-4 mb-4">
      <div class="col-lg-4">
        <WeeklySummaryPanel :summary="weeklySummary" />
      </div>
      <div class="col-lg-8">
        <div class="card border-0 shadow-sm rounded-4 p-4 h-100">
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h5 class="fw-bold m-0">Team Total Hours</h5>
            <AppBadge variant="primary">{{ teamHours.totalHours || 0 }}h</AppBadge>
          </div>
          <div class="d-flex flex-column gap-3">
            <div v-for="member in teamHours.memberHours || []" :key="member.userId" class="d-flex justify-content-between align-items-center border rounded-4 p-3">
              <span class="fw-semibold">{{ member.username }}</span>
              <AppBadge variant="secondary">{{ member.hours }}h</AppBadge>
            </div>
            <div v-if="!(teamHours.memberHours || []).length" class="text-muted small">Select a team to view totals.</div>
          </div>
        </div>
      </div>
    </div>

    <div class="row g-4">
      <div class="col-xl-6">
        <CompletedTasksTable :report="completedReport" />
      </div>
      <div class="col-xl-6">
        <TrendsChartSection :trends="trends" />
      </div>
    </div>
  </DashboardLayout>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import DashboardLayout from '../components/templates/DashboardLayout.vue'
import AppSelect from '../components/atoms/AppSelect.vue'
import AppBadge from '../components/atoms/AppBadge.vue'
import WeeklySummaryPanel from '../components/organisms/WeeklySummaryPanel.vue'
import CompletedTasksTable from '../components/organisms/CompletedTasksTable.vue'
import TrendsChartSection from '../components/organisms/TrendsChartSection.vue'
import { getTeams } from '../services/teamService'
import { getCompletedTasksReport, getTeamHours, getTrends, getWeeklySummary } from '../services/reportService'

const teams = ref([])
const selectedTeamId = ref('')
const weeklySummary = ref({ dailyHours: [] })
const teamHours = ref({ memberHours: [] })
const completedReport = ref({ tasks: [] })
const trends = ref({ hoursTrend: [], completedTasksTrend: [] })
const pageError = ref('')

const loadReports = async () => {
  weeklySummary.value = await getWeeklySummary()
  if (!selectedTeamId.value) return
  const teamId = Number(selectedTeamId.value)
  const [hours, completed, trendData] = await Promise.all([
    getTeamHours(teamId),
    getCompletedTasksReport(teamId),
    getTrends(teamId)
  ])
  teamHours.value = hours
  completedReport.value = completed
  trends.value = trendData
}

onMounted(async () => {
  try {
    teams.value = await getTeams()
    selectedTeamId.value = teams.value[0] ? String(teams.value[0].id) : ''
    await loadReports()
  } catch (error) {
    pageError.value = error.response?.data?.message || 'Could not load reports.'
  }
})
</script>
