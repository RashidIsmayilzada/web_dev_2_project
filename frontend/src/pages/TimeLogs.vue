<template>
  <DashboardLayout>
    <div class="d-flex justify-content-between align-items-center mb-5 header-slide">
      <div>
        <h2 class="fw-bold text-dark m-0">Time Logs</h2>
        <p class="text-muted small m-0 mt-1">Track your hours spent on tasks.</p>
      </div>
      <AppButton variant="primary" class="rounded-pill px-4 shadow-sm" @click="showCreateModal = true">+ Log Time</AppButton>
    </div>
    
    <div v-if="loading" class="text-center py-5">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <div v-else class="row g-4 cards-slide">
      <div class="col-md-6" v-for="log in timeLogs" :key="log.id">
        <div class="card border-0 shadow-sm rounded-4 h-100 p-4 transition-card">
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="fw-bold m-0 text-dark">Task ID: {{ log.taskId }} ({{ log.taskTitle }})</h6>
            <span class="badge bg-primary bg-opacity-10 text-primary rounded-pill px-3 py-2">
              <i class="bi bi-person me-1"></i> {{ log.username }}
            </span>
          </div>
          <p class="text-muted small mb-3 flex-grow-1 border-start border-3 border-primary ps-3 bg-light rounded-end py-2">
            {{ log.description || 'No description provided.' }}
          </p>
          <div class="d-flex justify-content-between align-items-center mt-auto text-secondary small bg-body-tertiary p-2 rounded-3 border">
            <div><strong>Start:</strong> {{ formatTime(log.startTime) }}</div>
            <div><strong>End:</strong> {{ formatTime(log.endTime) }}</div>
          </div>
        </div>
      </div>
      
      <div v-if="timeLogs.length === 0" class="col-12 text-center py-5 text-muted border-dashed rounded-4">
        <i class="bi bi-clock-history fs-1 text-light mb-3 d-block"></i>
        <h5>No Time Logged</h5>
        <p>You haven't logged any time yet. Click '+ Log Time' to get started.</p>
      </div>
    </div>

    <!-- Create Time Log Modal -->
    <div v-if="showCreateModal" class="modal-backdrop fade show" style="display: block;"></div>
    <div v-if="showCreateModal" class="modal fade show" tabindex="-1" style="display: block;">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow-lg rounded-4">
          <div class="modal-header border-0 pb-0">
            <h5 class="fw-bold text-dark">Log Time</h5>
            <button type="button" class="btn-close" @click="showCreateModal = false"></button>
          </div>
          <div class="modal-body py-4">
            <FormGroup label="Task ID" class="mb-3">
              <AppInput v-model="newLog.taskId" type="number" placeholder="Enter Task ID" />
            </FormGroup>
            <FormGroup label="Description" class="mb-3">
              <AppInput v-model="newLog.description" placeholder="What did you work on?" />
            </FormGroup>
            <div class="row">
              <div class="col-6">
                <FormGroup label="Start Time" class="mb-3">
                  <input type="datetime-local" class="form-control custom-input" v-model="newLog.startTime" />
                </FormGroup>
              </div>
              <div class="col-6">
                <FormGroup label="End Time" class="mb-3">
                  <input type="datetime-local" class="form-control custom-input" v-model="newLog.endTime" />
                </FormGroup>
              </div>
            </div>
          </div>
          <div class="modal-footer border-0 pt-0">
            <AppButton variant="light" class="text-secondary" @click="showCreateModal = false">Cancel</AppButton>
            <AppButton variant="primary" @click="logTime" :disabled="!newLog.taskId || !newLog.startTime || !newLog.endTime">Save</AppButton>
          </div>
        </div>
      </div>
    </div>
  </DashboardLayout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import DashboardLayout from '../components/templates/DashboardLayout.vue'
import AppButton from '../components/atoms/AppButton.vue'
import AppInput from '../components/atoms/AppInput.vue'
import FormGroup from '../components/molecules/FormGroup.vue'

const timeLogs = ref([])
const loading = ref(true)
const showCreateModal = ref(false)

const newLog = ref({
  taskId: null,
  description: '',
  startTime: '',
  endTime: ''
})

const fetchTimeLogs = async () => {
  try {
    loading.value = true
    const { data } = await axios.get('/api/timelogs')
    timeLogs.value = data
  } catch (error) {
    console.error("Failed to fetch time logs", error)
  } finally {
    loading.value = false
  }
}

const logTime = async () => {
  try {
    await axios.post('/api/timelogs', newLog.value)
    showCreateModal.value = false
    newLog.value = { taskId: null, description: '', startTime: '', endTime: '' }
    await fetchTimeLogs()
  } catch (error) {
    console.error("Failed to log time", error)
    alert("Could not log time. Ensure the Task ID exists.")
  }
}

const formatTime = (isoString) => {
  if (!isoString) return 'N/A';
  const d = new Date(isoString);
  return d.toLocaleString([], { dateStyle: 'short', timeStyle: 'short' });
}

onMounted(() => {
  fetchTimeLogs()
})
</script>

<style scoped>
.transition-card {
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}
.transition-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05) !important;
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
  border: 2px dashed #e9ecef !important;
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
