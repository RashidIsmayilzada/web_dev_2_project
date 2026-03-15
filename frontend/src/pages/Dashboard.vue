<template>
  <DashboardLayout>
    <div class="d-flex justify-content-between align-items-center mb-5 header-slide">
      <div>
        <h2 class="fw-bold text-dark m-0">Dashboard Overview</h2>
        <p class="text-muted small m-0 mt-1">Track your progress and team hours.</p>
      </div>
      <AppButton variant="primary" class="rounded-pill px-4 shadow-sm">+ New Project</AppButton>
    </div>
    
    <div class="row g-4 cards-slide">
      <div class="col-md-4">
        <div class="card border-0 shadow-sm rounded-4 h-100 p-4 transition-card">
          <h6 class="text-muted fw-semibold mb-3">Total Projects</h6>
          <h2 class="fw-bold m-0 text-dark">12</h2>
        </div>
      </div>
      <div class="col-md-4">
        <div class="card border-0 shadow-sm rounded-4 h-100 p-4 transition-card">
          <h6 class="text-muted fw-semibold mb-3">Active Tasks</h6>
          <h2 class="fw-bold m-0 text-dark">48</h2>
        </div>
      </div>
      <div class="col-md-4">
        <div class="card border-0 shadow-sm rounded-4 h-100 p-4 transition-card">
          <h6 class="text-muted fw-semibold mb-3">Hours Tracked</h6>
          <h2 class="fw-bold m-0 text-dark">126h</h2>
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

const stats = ref({
  totalProjects: 0,
  activeTasks: 0,
  hoursTracked: 0
})

onMounted(async () => {
  try {
    const { data } = await axios.get('/api/dashboard/stats')
    stats.value = data
  } catch (error) {
    console.error("Failed to load dashboard stats", error)
  }
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
</style>
