<template>
  <DashboardLayout>
    <div class="d-flex justify-content-between align-items-center mb-5 header-slide">
      <div>
        <h2 class="fw-bold text-dark m-0">Tasks</h2>
        <p class="text-muted small m-0 mt-1" v-if="projectId">Showing tasks for Project ID: {{ projectId }}</p>
        <p class="text-muted small m-0 mt-1" v-else>Please select a project to view tasks.</p>
      </div>
      <AppButton variant="primary" class="rounded-pill px-4 shadow-sm" @click="showCreateModal = true" v-if="projectId">+ Add Task</AppButton>
    </div>
    
    <div v-if="!projectId" class="text-center py-5 text-muted bg-white rounded-4 shadow-sm p-5 border-0">
      <i class="bi bi-inbox fs-1 text-light mb-3 d-block"></i>
      <h5>No Project Selected</h5>
      <p class="mb-4">Navigate to the Projects tab to select a project and view its tasks.</p>
      <router-link to="/projects" class="btn btn-primary rounded-pill px-4">Go to Projects</router-link>
    </div>

    <div v-else-if="loading" class="text-center py-5">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <div v-else class="row g-4 cards-slide">
      <!-- Kanban Board Columns -->
      <div class="col-md-4" v-for="status in ['TODO', 'IN_PROGRESS', 'DONE']" :key="status">
        <div class="bg-light rounded-4 p-3 h-100">
          <h6 class="fw-bold text-secondary mb-3 d-flex justify-content-between align-items-center">
            {{ formatStatus(status) }}
            <span class="badge bg-secondary rounded-pill">{{ getTasksByStatus(status).length }}</span>
          </h6>
          
          <div class="d-flex flex-column gap-3">
            <div 
              v-for="task in getTasksByStatus(status)" 
              :key="task.id" 
              class="card border-0 shadow-sm rounded-3 p-3 transition-card bg-white cursor-pointer"
            >
              <h6 class="fw-bold m-0 text-dark mb-2">{{ task.title }}</h6>
              <div class="d-flex justify-content-between align-items-center mt-2">
                <span class="small text-muted" v-if="task.assigneeName">
                  <i class="bi bi-person-circle me-1"></i> {{ task.assigneeName }}
                </span>
                <span class="small text-muted" v-else>Unassigned</span>
                
                <span class="badge rounded-pill" :class="getStatusBadgeClass(task.status)">
                  {{ task.status }}
                </span>
              </div>
            </div>
            
            <div v-if="getTasksByStatus(status).length === 0" class="text-center py-4 text-muted small border border-dashed rounded-3">
              No tasks
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Create Task Modal -->
    <div v-if="showCreateModal" class="modal-backdrop fade show" style="display: block;"></div>
    <div v-if="showCreateModal" class="modal fade show" tabindex="-1" style="display: block;">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow-lg rounded-4">
          <div class="modal-header border-0 pb-0">
            <h5 class="fw-bold text-dark">Create New Task</h5>
            <button type="button" class="btn-close" @click="showCreateModal = false"></button>
          </div>
          <div class="modal-body py-4">
            <FormGroup label="Task Title" class="mb-3">
              <AppInput v-model="newTask.title" placeholder="E.g. Update Landing Page" />
            </FormGroup>
            <FormGroup label="Assignee ID (Optional)" class="mb-3">
              <AppInput v-model="newTask.assigneeId" type="number" placeholder="Enter User ID" />
            </FormGroup>
            <FormGroup label="Status" class="mb-3">
              <select class="form-select custom-input" v-model="newTask.status">
                <option value="TODO">To Do</option>
                <option value="IN_PROGRESS">In Progress</option>
                <option value="DONE">Done</option>
              </select>
            </FormGroup>
          </div>
          <div class="modal-footer border-0 pt-0">
            <AppButton variant="light" class="text-secondary" @click="showCreateModal = false">Cancel</AppButton>
            <AppButton variant="primary" @click="createTask" :disabled="!newTask.title">Create</AppButton>
          </div>
        </div>
      </div>
    </div>
  </DashboardLayout>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import DashboardLayout from '../components/templates/DashboardLayout.vue'
import AppButton from '../components/atoms/AppButton.vue'
import AppInput from '../components/atoms/AppInput.vue'
import FormGroup from '../components/molecules/FormGroup.vue'

const route = useRoute()
const projectId = ref(route.query.project || null)
const tasks = ref([])
const loading = ref(false)
const showCreateModal = ref(false)

const newTask = ref({
  title: '',
  status: 'TODO',
  assigneeId: null,
  projectId: null
})

const fetchTasks = async () => {
  if (!projectId.value) return;
  try {
    loading.value = true
    const { data } = await axios.get(`/api/tasks/project/${projectId.value}`)
    tasks.value = data
  } catch (error) {
    console.error("Failed to fetch tasks", error)
  } finally {
    loading.value = false
  }
}

const createTask = async () => {
  try {
    const payload = {
      ...newTask.value,
      projectId: parseInt(projectId.value)
    }
    if (!payload.assigneeId) delete payload.assigneeId;
    
    await axios.post('/api/tasks', payload)
    showCreateModal.value = false
    newTask.value = { title: '', status: 'TODO', assigneeId: null, projectId: null }
    await fetchTasks()
  } catch (error) {
    console.error("Failed to create task", error)
    alert("Could not create task.")
  }
}

const getTasksByStatus = (status) => {
  return tasks.value.filter(t => t.status === status)
}

const formatStatus = (status) => {
  return status.replace('_', ' ').replace(/\w\S*/g, (w) => (w.replace(/^\w/, (c) => c.toUpperCase())));
}

const getStatusBadgeClass = (status) => {
  if (status === 'DONE') return 'bg-success bg-opacity-10 text-success';
  if (status === 'IN_PROGRESS') return 'bg-primary bg-opacity-10 text-primary';
  return 'bg-secondary bg-opacity-10 text-secondary';
}

onMounted(() => {
  if (projectId.value) fetchTasks()
})

watch(() => route.query.project, (newVal) => {
  projectId.value = newVal
  if (newVal) fetchTasks()
})
</script>

<style scoped>
.transition-card {
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}
.transition-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08) !important;
}
.header-slide {
  animation: fadeInUp 0.5s ease both;
}
.cards-slide {
  animation: fadeInUp 0.5s ease both;
  animation-delay: 0.1s;
}
@keyframes fadeInUp {
  from { opacity: 0; transform: translateY(15px); }
  to { opacity: 1; transform: translateY(0); }
}

.border-dashed {
  border-style: dashed !important;
}
.cursor-pointer {
  cursor: pointer;
}

/* Modal specific inputs */
.modal-content .custom-input {
  border: 1px solid #dee2e6;
  background: #f8f9fa;
  color: #212529;
  border-radius: 8px;
  padding: 0.375rem 0.75rem;
}
.modal-content .custom-input:focus {
  background: #fff;
  border-color: #0d6efd;
  box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
}
.modal-content .form-label {
  color: #495057 !important;
}
</style>
