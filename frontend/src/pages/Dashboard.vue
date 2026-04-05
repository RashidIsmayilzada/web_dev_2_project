<template>
  <DashboardLayout>
    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-3">
      <div>
        <h2 class="fw-bold text-dark m-0">Dashboard Overview</h2>
        <p class="text-muted small m-0 mt-1">Personal productivity, tasks, time, and invite status.</p>
      </div>
      <div class="d-flex gap-2 flex-wrap">
        <AppButton variant="dark" class="rounded-pill px-4" @click="router.push('/teams')">Teams</AppButton>
        <AppButton variant="primary" class="rounded-pill px-4" @click="router.push('/reports')">Reports</AppButton>
      </div>
    </div>

    <div v-if="errorMessage" class="alert alert-danger rounded-4 border-0 shadow-sm">{{ errorMessage }}</div>

    <div class="row g-4 mb-4">
      <div class="col-md-3">
        <StatCard label="Teams" :value="overview.stats?.totalTeams || 0" />
      </div>
      <div class="col-md-3">
        <StatCard label="Projects" :value="overview.stats?.totalProjects || 0" />
      </div>
      <div class="col-md-3">
        <StatCard label="Active Tasks" :value="overview.stats?.activeTasks || 0" />
      </div>
      <div class="col-md-3">
        <StatCard label="Pending Invites" :value="overview.stats?.pendingInvites || 0" />
      </div>
    </div>

    <div class="row g-4">
      <div class="col-xl-6">
        <div class="card border-0 shadow-sm rounded-4 p-4 h-100">
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h5 class="fw-bold m-0">Recent Tasks</h5>
            <AppBadge variant="primary">{{ overview.recentTasks?.length || 0 }}</AppBadge>
          </div>
          <div class="d-flex flex-column gap-3">
            <div v-for="task in overview.recentTasks || []" :key="task.id" class="border rounded-4 p-3">
              <div class="d-flex justify-content-between align-items-center mb-1">
                <span class="fw-semibold">{{ task.title }}</span>
                <AppBadge :variant="task.status === 'DONE' ? 'success' : task.status === 'IN_PROGRESS' ? 'primary' : 'secondary'">
                  {{ task.status }}
                </AppBadge>
              </div>
              <div class="small text-muted">{{ task.projectName }}</div>
            </div>
            <div v-if="!(overview.recentTasks || []).length" class="text-muted small">No recent tasks yet.</div>
          </div>
        </div>
      </div>
      <div class="col-xl-6">
        <div class="card border-0 shadow-sm rounded-4 p-4 h-100">
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h5 class="fw-bold m-0">Recent Time Logs</h5>
            <AppBadge variant="success">{{ overview.recentTimeLogs?.length || 0 }}</AppBadge>
          </div>
          <div class="d-flex flex-column gap-3">
            <div v-for="log in overview.recentTimeLogs || []" :key="log.id" class="border rounded-4 p-3">
              <div class="fw-semibold">{{ log.taskTitle }}</div>
              <div class="small text-muted">{{ log.projectName }}</div>
              <div class="small mt-1">{{ formatDate(log.startTime) }}</div>
            </div>
            <div v-if="!(overview.recentTimeLogs || []).length" class="text-muted small">No recent time logs yet.</div>
          </div>
        </div>
      </div>
    </div>
  </DashboardLayout>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import DashboardLayout from '../components/templates/DashboardLayout.vue'
import AppButton from '../components/atoms/AppButton.vue'
import AppBadge from '../components/atoms/AppBadge.vue'
import StatCard from '../components/molecules/StatCard.vue'
import { getOverview } from '../services/dashboardService'

const router = useRouter()
const overview = ref({})
const errorMessage = ref('')

const formatDate = (value) => new Date(value).toLocaleString([], { dateStyle: 'medium', timeStyle: 'short' })

onMounted(async () => {
  try {
    overview.value = await getOverview()
  } catch (error) {
    errorMessage.value = error.response?.data?.message || 'Failed to load dashboard stats'
  }
})
</script>
