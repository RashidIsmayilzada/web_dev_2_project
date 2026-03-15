<template>
  <DashboardLayout>
    <div class="d-flex justify-content-between align-items-center mb-5 header-slide">
      <div>
        <h2 class="fw-bold text-dark m-0">Teams</h2>
        <p class="text-muted small m-0 mt-1">Manage your team memberships.</p>
      </div>
      <AppButton variant="primary" class="rounded-pill px-4 shadow-sm" @click="showCreateModal = true">+ Create Team</AppButton>
    </div>
    
    <div v-if="loading" class="text-center py-5">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <div v-else class="row g-4 cards-slide">
      <div class="col-md-4" v-for="team in teams" :key="team.id">
        <div class="card border-0 shadow-sm rounded-4 h-100 p-4 transition-card">
          <div class="d-flex justify-content-between align-items-center mb-2">
            <h5 class="fw-bold m-0 text-dark">{{ team.name }}</h5>
            <span class="badge bg-success bg-opacity-10 text-success rounded-pill">{{ team.currentUserRole }}</span>
          </div>
          <p class="text-muted small mb-4 flex-grow-1">{{ team.description }}</p>
          <div class="mt-auto">
            <span class="badge bg-dark bg-opacity-10 text-dark px-3 py-2 rounded-pill shadow-sm">
               <i class="bi bi-key me-1"></i> Team ID: {{ team.id }}
            </span>
          </div>
        </div>
      </div>
      
      <div v-if="teams.length === 0" class="col-12 text-center py-5 text-muted">
        You are not part of any teams. Create one to get started!
      </div>
    </div>

    <!-- Create Team Modal -->
    <div v-if="showCreateModal" class="modal-backdrop fade show" style="display: block;"></div>
    <div v-if="showCreateModal" class="modal fade show" tabindex="-1" style="display: block;">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow-lg rounded-4">
          <div class="modal-header border-0 pb-0">
            <h5 class="fw-bold text-dark">Create New Team</h5>
            <button type="button" class="btn-close" @click="showCreateModal = false"></button>
          </div>
          <div class="modal-body py-4">
            <FormGroup label="Team Name" class="mb-3">
              <AppInput v-model="newTeam.name" placeholder="E.g. Engineering Squad" />
            </FormGroup>
            <FormGroup label="Description" class="mb-3">
              <AppInput v-model="newTeam.description" placeholder="Short description..." />
            </FormGroup>
          </div>
          <div class="modal-footer border-0 pt-0">
            <AppButton variant="light" class="text-secondary" @click="showCreateModal = false">Cancel</AppButton>
            <AppButton variant="primary" @click="createTeam" :disabled="!newTeam.name">Create</AppButton>
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

const teams = ref([])
const loading = ref(true)
const showCreateModal = ref(false)
const newTeam = ref({
  name: '',
  description: ''
})

const fetchTeams = async () => {
  try {
    loading.value = true
    const { data } = await axios.get('/api/teams')
    teams.value = data
  } catch (error) {
    console.error("Failed to fetch teams", error)
  } finally {
    loading.value = false
  }
}

const createTeam = async () => {
  try {
    await axios.post('/api/teams', newTeam.value)
    showCreateModal.value = false
    newTeam.value = { name: '', description: '' }
    await fetchTeams()
  } catch (error) {
    console.error("Failed to create team", error)
    alert("Could not create team.")
  }
}

onMounted(() => {
  fetchTeams()
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

/* Modal specific inputs */
.modal-content .custom-input {
  border: 1px solid #dee2e6;
  background: #f8f9fa;
  color: #212529;
}
.modal-content .custom-input:focus {
  background: #fff;
}
.modal-content .form-label {
  color: #495057 !important;
}
</style>
