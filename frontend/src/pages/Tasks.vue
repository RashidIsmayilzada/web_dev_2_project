<template>
  <DashboardLayout>
    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-3">
      <div>
        <h2 class="fw-bold text-dark m-0">Tasks</h2>
        <p class="text-muted small m-0 mt-1">Filter tasks, assign work, and start timers.</p>
      </div>
      <AppButton variant="primary" class="rounded-pill px-4" @click="openCreateModal" :disabled="!taskFilters.projectId">
        + Add Task
      </AppButton>
    </div>

    <FilterBar v-model="taskFilters" :fields="filterFields" @apply="loadTasks" @reset="resetFilters" />

    <div class="row g-4 my-4">
      <div class="col-md-4">
        <StatCard label="Filtered Tasks" :value="tasks.length" />
      </div>
      <div class="col-md-4">
        <StatCard label="In Progress" :value="tasks.filter((task) => task.status === 'IN_PROGRESS').length" />
      </div>
      <div class="col-md-4">
        <StatCard label="Done" :value="tasks.filter((task) => task.status === 'DONE').length" />
      </div>
    </div>

    <div v-if="pageError" class="alert alert-danger rounded-4 border-0 shadow-sm">{{ pageError }}</div>
    <div v-if="pageSuccess" class="alert alert-success rounded-4 border-0 shadow-sm">{{ pageSuccess }}</div>

    <div class="mb-4">
      <TimerControl :active-timer="activeTimer" @stop="stopActiveTimer" @cancel="cancelActiveTimer" />
    </div>

    <div class="card border-0 shadow-sm rounded-4 p-4">
      <div class="row g-4">
        <div class="col-lg-4" v-for="status in statuses" :key="status">
          <div class="border rounded-4 p-3 h-100 bg-light-subtle">
            <div class="d-flex justify-content-between align-items-center mb-3">
              <h6 class="fw-bold m-0">{{ formatStatus(status) }}</h6>
              <AppBadge :variant="status === 'DONE' ? 'success' : status === 'IN_PROGRESS' ? 'primary' : 'secondary'">
                {{ tasksByStatus(status).length }}
              </AppBadge>
            </div>

            <div class="d-flex flex-column gap-3">
              <div v-for="task in tasksByStatus(status)" :key="task.id" class="card border-0 shadow-sm rounded-4 p-3">
                <div class="d-flex justify-content-between align-items-start gap-2">
                  <div>
                    <h6 class="fw-bold mb-1">{{ task.title }}</h6>
                    <p class="text-muted small mb-2">{{ task.projectName }}</p>
                  </div>
                  <AppBadge :variant="status === 'DONE' ? 'success' : status === 'IN_PROGRESS' ? 'primary' : 'secondary'">
                    {{ formatStatus(task.status) }}
                  </AppBadge>
                </div>

                <div class="small text-muted mb-3">Assigned to {{ task.assigneeName || 'Unassigned' }}</div>

                <div class="d-flex gap-2 flex-wrap">
                  <AppButton variant="light" @click="openEditModal(task)">Edit</AppButton>
                  <AppButton
                    variant="dark"
                    @click="startTaskTimer(task)"
                    :disabled="Boolean(activeTimer)"
                  >
                    {{ activeTimer && activeTimer.taskId === task.id ? 'Timer Running' : 'Start Timer' }}
                  </AppButton>
                  <AppButton variant="danger" @click="removeTask(task.id)">Delete</AppButton>
                </div>
              </div>
              <div v-if="tasksByStatus(status).length === 0" class="text-muted small">No tasks here.</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-if="showTaskModal" class="modal-backdrop fade show" style="display: block;"></div>
    <div v-if="showTaskModal" class="modal fade show" tabindex="-1" style="display: block;">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow-lg rounded-4">
          <div class="modal-header border-0 pb-0">
            <h5 class="fw-bold text-dark">{{ editingTaskId ? 'Edit Task' : 'Create Task' }}</h5>
            <button type="button" class="btn-close" @click="closeTaskModal"></button>
          </div>
          <div class="modal-body py-4">
            <FormGroup label="Title">
              <AppInput v-model="taskForm.title" />
            </FormGroup>
            <FormGroup label="Status">
              <AppSelect v-model="taskForm.status">
                <option value="TODO">To Do</option>
                <option value="IN_PROGRESS">In Progress</option>
                <option value="DONE">Done</option>
              </AppSelect>
            </FormGroup>
            <FormGroup label="Assign To" class="mb-0">
              <AppSelect v-model="taskForm.assigneeUsername">
                <option value="">Unassigned</option>
                <option v-for="member in assignableMembers" :key="member.userId" :value="member.username">
                  {{ member.username }} ({{ member.role }})
                </option>
              </AppSelect>
            </FormGroup>
          </div>
          <div class="modal-footer border-0 pt-0">
            <AppButton variant="light" @click="closeTaskModal">Cancel</AppButton>
            <AppButton variant="primary" @click="saveTask" :disabled="!taskForm.title.trim()">Save</AppButton>
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
import FilterBar from '../components/molecules/FilterBar.vue'
import StatCard from '../components/molecules/StatCard.vue'
import TimerControl from '../components/molecules/TimerControl.vue'
import { getProjects } from '../services/projectService'
import { createTask, deleteTask, getTasks, updateTask } from '../services/taskService'
import { getTeamMembers, getTeams } from '../services/teamService'
import { cancelTimer, getActiveTimer, startTimer, stopTimer } from '../services/timerService'

const statuses = ['TODO', 'IN_PROGRESS', 'DONE']
const teams = ref([])
const projects = ref([])
const tasks = ref([])
const assignableMembers = ref([])
const pageError = ref('')
const pageSuccess = ref('')
const activeTimer = ref(null)
const showTaskModal = ref(false)
const editingTaskId = ref(null)
const taskFilters = ref({
  teamId: '',
  projectId: '',
  status: '',
  assignee: '',
  search: ''
})

const taskForm = ref({
  title: '',
  status: 'TODO',
  assigneeUsername: ''
})

const filterFields = computed(() => [
  {
    key: 'teamId',
    label: 'Team',
    type: 'select',
    placeholder: 'All teams',
    options: teams.value.map((team) => ({ value: String(team.id), label: team.name }))
  },
  {
    key: 'projectId',
    label: 'Project',
    type: 'select',
    placeholder: 'All projects',
    options: projects.value
      .filter((project) => !taskFilters.value.teamId || String(project.teamId) === String(taskFilters.value.teamId))
      .map((project) => ({ value: String(project.id), label: project.name }))
  },
  { key: 'status', label: 'Status', type: 'select', placeholder: 'All statuses', options: statuses.map((status) => ({ value: status, label: formatStatus(status) })) },
  { key: 'assignee', label: 'Assignee', placeholder: 'Search by assignee' },
  { key: 'search', label: 'Search', placeholder: 'Search titles' }
])

const normalizedFilters = () => {
  const filters = {}
  Object.entries(taskFilters.value).forEach(([key, value]) => {
    if (value !== '' && value != null) {
      filters[key] = ['teamId', 'projectId'].includes(key) ? Number(value) : value
    }
  })
  return filters
}

const loadData = async () => {
  teams.value = await getTeams()
  projects.value = await getProjects()
  await Promise.all([loadTasks(), loadActiveTimer()])
}

const loadTasks = async () => {
  tasks.value = await getTasks(normalizedFilters())
  await loadAssignableMembers()
}

const loadActiveTimer = async () => {
  activeTimer.value = await getActiveTimer()
}

const loadAssignableMembers = async () => {
  if (!taskFilters.value.teamId) {
    assignableMembers.value = []
    return
  }
  assignableMembers.value = await getTeamMembers(Number(taskFilters.value.teamId))
}

const resetFilters = async () => {
  taskFilters.value = { teamId: '', projectId: '', status: '', assignee: '', search: '' }
  assignableMembers.value = []
  await loadTasks()
}

const tasksByStatus = (status) => tasks.value.filter((task) => task.status === status)

const formatStatus = (status) => status.replace('_', ' ').replace(/\b\w/g, (letter) => letter.toUpperCase())

const openCreateModal = async () => {
  editingTaskId.value = null
  taskForm.value = { title: '', status: 'TODO', assigneeUsername: '' }
  await loadAssignableMembers()
  showTaskModal.value = true
}

const openEditModal = async (task) => {
  editingTaskId.value = task.id
  taskFilters.value.projectId = String(task.projectId)
  taskFilters.value.teamId = String(task.teamId)
  await loadAssignableMembers()
  taskForm.value = { title: task.title, status: task.status, assigneeUsername: task.assigneeUsername || '' }
  showTaskModal.value = true
}

const closeTaskModal = () => {
  showTaskModal.value = false
}

const saveTask = async () => {
  const payload = {
    title: taskForm.value.title,
    status: taskForm.value.status,
    projectId: Number(taskFilters.value.projectId),
    assigneeUsername: taskForm.value.assigneeUsername
  }

  if (editingTaskId.value) {
    await updateTask(editingTaskId.value, payload)
  } else {
    await createTask(payload)
  }

  closeTaskModal()
  await loadTasks()
}

const removeTask = async (taskId) => {
  await deleteTask(taskId)
  await loadTasks()
}

const startTaskTimer = async (task) => {
  try {
    pageError.value = ''
    pageSuccess.value = ''
    const timer = await startTimer({ taskId: task.id, description: `Working on ${task.title}` })
    activeTimer.value = timer
    pageSuccess.value = `Started timer for "${task.title}".`
  } catch (error) {
    pageError.value = error.response?.data?.message || 'Could not start the timer.'
  }
}

const stopActiveTimer = async () => {
  if (!activeTimer.value) return
  try {
    pageError.value = ''
    pageSuccess.value = ''
    await stopTimer(activeTimer.value.id, {})
    pageSuccess.value = `Stopped timer for "${activeTimer.value.taskTitle}" and created a time log.`
    activeTimer.value = null
  } catch (error) {
    pageError.value = error.response?.data?.message || 'Could not stop the timer.'
  }
}

const cancelActiveTimer = async () => {
  if (!activeTimer.value) return
  try {
    pageError.value = ''
    pageSuccess.value = ''
    await cancelTimer(activeTimer.value.id)
    pageSuccess.value = `Cancelled timer for "${activeTimer.value.taskTitle}".`
    activeTimer.value = null
  } catch (error) {
    pageError.value = error.response?.data?.message || 'Could not cancel the timer.'
  }
}

onMounted(async () => {
  try {
    await loadData()
  } catch (error) {
    pageError.value = error.response?.data?.message || 'Could not load tasks.'
  }
})
</script>
