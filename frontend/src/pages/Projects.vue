<template>
  <DashboardLayout>
    <div class="d-flex justify-content-between align-items-center mb-5 header-slide">
      <div>
        <h2 class="fw-bold text-dark m-0">Projects</h2>
        <p class="text-muted small m-0 mt-1">Manage your team's projects.</p>
      </div>
      <AppButton
        variant="primary"
        class="rounded-pill px-4 shadow-sm"
        @click="openCreateModal"
        :disabled="teams.length === 0"
      >
        + New Project
      </AppButton>
    </div>

    <div v-if="!loading && teams.length === 0" class="alert alert-warning border-0 rounded-4 shadow-sm mb-4 d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3">
      <div>
        Create a team first before creating projects.
      </div>
      <router-link to="/teams" class="btn btn-sm btn-dark rounded-pill px-3">
        Go to Teams
      </router-link>
    </div>
    
    <div v-if="loading" class="text-center py-5">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <div v-else class="row g-4 cards-slide">
      <div class="col-md-4" v-for="project in projects" :key="project.id">
        <div class="card border-0 shadow-sm rounded-4 h-100 p-4 transition-card">
          <h5 class="fw-bold m-0 text-dark mb-2">{{ project.name }}</h5>
          <p class="text-muted small mb-4 flex-grow-1">{{ project.description }}</p>
          <div class="d-flex justify-content-between align-items-center mt-auto">
            <span class="badge bg-primary bg-opacity-10 text-primary px-3 py-2 rounded-pill">Team ID: {{ project.teamId }}</span>
            <router-link :to="`/tasks?project=${project.id}`" class="btn btn-sm btn-outline-primary rounded-pill px-3">View Tasks</router-link>
          </div>
        </div>
      </div>
      
      <div v-if="projects.length === 0" class="col-12 text-center py-5 text-muted">
        <p class="mb-3">No projects found. Create one to get started!</p>
        <router-link v-if="teams.length === 0" to="/teams" class="btn btn-outline-dark rounded-pill px-4">
          Create a Team First
        </router-link>
      </div>
    </div>

    <!-- Simple Create Modal -->
    <div v-if="showCreateModal" class="modal-backdrop fade show" style="display: block;"></div>
    <div v-if="showCreateModal" class="modal fade show" tabindex="-1" style="display: block;">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow-lg rounded-4">
          <div class="modal-header border-0 pb-0">
            <h5 class="fw-bold text-dark">Create New Project</h5>
            <button type="button" class="btn-close" @click="showCreateModal = false"></button>
          </div>
          <div class="modal-body py-4">
            <FormGroup label="Project Name" class="mb-3">
              <AppInput v-model="newProject.name" placeholder="E.g. Website Redesign" />
            </FormGroup>
            <FormGroup label="Description" class="mb-3">
              <AppInput v-model="newProject.description" placeholder="Short description..." />
            </FormGroup>
            <FormGroup label="Team" class="mb-3">
              <select v-model="newProject.teamId" class="form-select custom-input">
                <option :value="null" disabled>Select a team</option>
                <option v-for="team in teams" :key="team.id" :value="team.id">
                  {{ team.name }} (ID: {{ team.id }})
                </option>
              </select>
            </FormGroup>
            <div v-if="formError" class="text-danger small bg-danger bg-opacity-10 py-2 px-3 rounded-3">
              {{ formError }}
            </div>
          </div>
          <div class="modal-footer border-0 pt-0">
            <AppButton variant="light" class="text-secondary" @click="showCreateModal = false">Cancel</AppButton>
            <AppButton variant="primary" @click="createProject" :disabled="!canCreateProject">Create</AppButton>
          </div>
        </div>
      </div>
    </div>
  </DashboardLayout>
</template>

<script setup>
import { computed, ref, onMounted } from 'vue'
import axios from 'axios'
import DashboardLayout from '../components/templates/DashboardLayout.vue'
import AppButton from '../components/atoms/AppButton.vue'
import AppInput from '../components/atoms/AppInput.vue'
import FormGroup from '../components/molecules/FormGroup.vue'

const projects = ref([])
const teams = ref([])
const loading = ref(true)
const showCreateModal = ref(false)
const formError = ref('')
const newProject = ref({
  name: '',
  description: '',
  teamId: null
})

const canCreateProject = computed(() => {
  return newProject.value.name.trim() && newProject.value.teamId
})

const fetchProjects = async () => {
  try {
    const { data } = await axios.get('/api/projects')
    projects.value = data
  } catch (error) {
    console.error("Failed to fetch projects", error)
  } finally {
  }
}

const fetchTeams = async () => {
  try {
    const { data } = await axios.get('/api/teams')
    teams.value = data
  } catch (error) {
    console.error("Failed to fetch teams", error)
  }
}

const resetProjectForm = () => {
  newProject.value = {
    name: '',
    description: '',
    teamId: teams.value[0]?.id ?? null
  }
  formError.value = ''
}

const openCreateModal = () => {
  if (teams.value.length === 0) {
    formError.value = 'Create a team first before creating a project.'
    return
  }

  resetProjectForm()
  showCreateModal.value = true
}

const createProject = async () => {
  try {
    formError.value = ''

    const payload = {
      name: newProject.value.name.trim(),
      description: newProject.value.description.trim(),
      teamId: Number(newProject.value.teamId)
    }

    await axios.post('/api/projects', payload)
    showCreateModal.value = false
    resetProjectForm()
    await fetchProjects()
  } catch (error) {
    console.error("Failed to create project", error)
    formError.value = error.response?.data?.message || "Could not create project."
  }
}

onMounted(async () => {
  loading.value = true
  await Promise.all([fetchProjects(), fetchTeams()])
  resetProjectForm()
  loading.value = false
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
.modal-content .custom-input option {
  color: #212529;
}
.modal-content .custom-input:focus {
  background: #fff;
}
.modal-content .form-label {
  color: #495057 !important;
}
</style>
