<template>
  <DashboardLayout>
    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-3">
      <div>
        <h2 class="fw-bold text-dark m-0">Time Logs</h2>
        <p class="text-muted small m-0 mt-1">Manual logging, active timer control, and weekly visibility.</p>
      </div>
      <AppButton variant="primary" class="rounded-pill px-4" @click="showCreateModal = true">+ Log Time</AppButton>
    </div>

    <div v-if="pageError" class="alert alert-danger rounded-4 border-0 shadow-sm">{{ pageError }}</div>

    <div class="row g-4 mb-4">
      <div class="col-lg-8">
        <TimerControl :active-timer="activeTimer" @stop="stopActiveTimer" @cancel="cancelActiveTimerSession" />
      </div>
      <div class="col-lg-4">
        <WeeklySummaryPanel :summary="weeklySummary" />
      </div>
    </div>

    <div class="row g-4 mb-4">
      <div class="col-md-4">
        <StatCard label="Entries" :value="timeLogs.length" />
      </div>
      <div class="col-md-4">
        <StatCard label="Hours Logged" :value="`${totalHours}h`" />
      </div>
      <div class="col-md-4">
        <StatCard label="Tracked Source" :value="activeTimer ? 'Timer running' : 'Manual + Timer'" />
      </div>
    </div>

    <div class="card border-0 shadow-sm rounded-4 p-4">
      <div class="table-responsive">
        <table class="table align-middle mb-0">
          <thead>
            <tr>
              <th>Task</th>
              <th>Project</th>
              <th>Window</th>
              <th>Source</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="log in timeLogs" :key="log.id">
              <td>
                <div class="fw-semibold">{{ log.taskTitle }}</div>
                <div class="text-muted small">{{ log.description || 'No description' }}</div>
              </td>
              <td>{{ log.projectName }}</td>
              <td>{{ formatRange(log.startTime, log.endTime) }}</td>
              <td><AppBadge :variant="log.source === 'TIMER' ? 'primary' : 'secondary'">{{ log.source }}</AppBadge></td>
            </tr>
            <tr v-if="timeLogs.length === 0">
              <td colspan="4" class="text-muted">No time logs yet.</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="showCreateModal" class="modal-backdrop fade show" style="display: block;"></div>
    <div v-if="showCreateModal" class="modal fade show" tabindex="-1" style="display: block;">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow-lg rounded-4">
          <div class="modal-header border-0 pb-0">
            <h5 class="fw-bold text-dark">Manual Time Log</h5>
            <button type="button" class="btn-close" @click="showCreateModal = false"></button>
          </div>
          <div class="modal-body py-4">
            <FormGroup label="Task">
              <AppSelect v-model="newLog.taskId">
                <option value="">Select a task</option>
                <option v-for="task in tasks" :key="task.id" :value="String(task.id)">
                  {{ task.title }} ({{ task.projectName }})
                </option>
              </AppSelect>
            </FormGroup>
            <FormGroup label="Description">
              <AppInput v-model="newLog.description" />
            </FormGroup>
            <div class="row">
              <div class="col-md-6">
                <FormGroup label="Start Time">
                  <input v-model="newLog.startTime" type="datetime-local" class="form-control" />
                </FormGroup>
              </div>
              <div class="col-md-6">
                <FormGroup label="End Time" class="mb-0">
                  <input v-model="newLog.endTime" type="datetime-local" class="form-control" />
                </FormGroup>
              </div>
            </div>
          </div>
          <div class="modal-footer border-0 pt-0">
            <AppButton variant="light" @click="showCreateModal = false">Cancel</AppButton>
            <AppButton variant="primary" @click="createManualLog" :disabled="!newLog.taskId">Save</AppButton>
          </div>
        </div>
      </div>
    </div>
  </DashboardLayout>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import DashboardLayout from '../components/templates/DashboardLayout.vue'
import AppButton from '../components/atoms/AppButton.vue'
import AppInput from '../components/atoms/AppInput.vue'
import AppSelect from '../components/atoms/AppSelect.vue'
import AppBadge from '../components/atoms/AppBadge.vue'
import FormGroup from '../components/molecules/FormGroup.vue'
import StatCard from '../components/molecules/StatCard.vue'
import TimerControl from '../components/molecules/TimerControl.vue'
import WeeklySummaryPanel from '../components/organisms/WeeklySummaryPanel.vue'
import { getTasks } from '../services/taskService'
import { createTimeLog, getTimeLogs } from '../services/timeLogService'
import { cancelTimer, getActiveTimer, stopTimer } from '../services/timerService'
import { getWeeklySummary } from '../services/reportService'

const timeLogs = ref([])
const tasks = ref([])
const activeTimer = ref(null)
const weeklySummary = ref({ dailyHours: [] })
const showCreateModal = ref(false)
const pageError = ref('')
const newLog = ref({
  taskId: '',
  description: '',
  startTime: '',
  endTime: ''
})

const totalHours = computed(() => {
  const totalMs = timeLogs.value.reduce((sum, log) => sum + Math.max(0, new Date(log.endTime) - new Date(log.startTime)), 0)
  return (totalMs / 3600000).toFixed(1)
})

const initializeForm = () => {
  const now = new Date()
  const oneHourAgo = new Date(now.getTime() - 60 * 60 * 1000)
  const formatLocal = (date) => new Date(date.getTime() - date.getTimezoneOffset() * 60000).toISOString().slice(0, 16)
  newLog.value = {
    taskId: tasks.value[0] ? String(tasks.value[0].id) : '',
    description: '',
    startTime: formatLocal(oneHourAgo),
    endTime: formatLocal(now)
  }
}

const loadPage = async () => {
  const [logData, taskData, timerData, summaryData] = await Promise.all([
    getTimeLogs(),
    getTasks(),
    getActiveTimer(),
    getWeeklySummary()
  ])
  timeLogs.value = logData
  tasks.value = taskData
  activeTimer.value = timerData
  weeklySummary.value = summaryData
  initializeForm()
}

const createManualLog = async () => {
  await createTimeLog({
    taskId: Number(newLog.value.taskId),
    description: newLog.value.description,
    startTime: newLog.value.startTime,
    endTime: newLog.value.endTime
  })
  showCreateModal.value = false
  await loadPage()
}

const stopActiveTimer = async () => {
  if (!activeTimer.value) return
  await stopTimer(activeTimer.value.id, {})
  await loadPage()
}

const cancelActiveTimerSession = async () => {
  if (!activeTimer.value) return
  await cancelTimer(activeTimer.value.id)
  await loadPage()
}

const formatRange = (start, end) => {
  return `${new Date(start).toLocaleString([], { dateStyle: 'medium', timeStyle: 'short' })} - ${new Date(end).toLocaleString([], { timeStyle: 'short' })}`
}

onMounted(async () => {
  try {
    await loadPage()
  } catch (error) {
    pageError.value = error.response?.data?.message || 'Could not load time logs.'
  }
})
</script>
