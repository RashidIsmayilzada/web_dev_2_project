<template>
  <div class="d-flex min-vh-100 bg-light-fade">
    <!-- Sidebar -->
    <div class="sidebar text-white p-4 d-flex flex-column shadow-lg">
      <div class="mb-5 d-flex align-items-center gap-2 brand-animation">
        <div class="rounded-circle bg-primary bg-gradient p-2"></div>
        <h4 class="fw-bold m-0" style="letter-spacing: 1px;">ProductivityHub</h4>
      </div>
      
      <ul class="nav flex-column gap-3 mb-auto nav-links-animation">
        <li class="nav-item">
          <router-link to="/dashboard" class="nav-link text-white rounded-3 px-3 py-2 fw-semibold transition-all">
            <i class="bi bi-grid-fill me-2"></i> Dashboard
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/projects" class="nav-link text-white rounded-3 px-3 py-2 fw-semibold transition-all">
            <i class="bi bi-folder-fill me-2"></i> Projects
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/tasks" class="nav-link text-white rounded-3 px-3 py-2 fw-semibold transition-all">
            <i class="bi bi-check-square-fill me-2"></i> Tasks
          </router-link>
        </li>
      </ul>
      
      <div class="mt-auto nav-links-animation" style="animation-delay: 0.1s;">
        <hr class="border-white border-opacity-25" />
        <a href="#" @click.prevent="logout" class="nav-link text-white-50 hover-white rounded-3 px-3 py-2 transition-all">
          <i class="bi bi-box-arrow-right me-2"></i> Logout
        </a>
      </div>
    </div>
    
    <!-- Main Content -->
    <div class="flex-grow-1 p-5 main-content-animation overflow-auto">
      <slot />
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from '../../stores/authStore.js'
import { useRouter } from 'vue-router'
const authStore = useAuthStore()
const router = useRouter()

const logout = () => {
  authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.bg-light-fade {
  background: #f8f9fa;
}
.sidebar {
  width: 280px;
  background: linear-gradient(180deg, #111827, #1f2937);
}
.transition-all {
  transition: all 0.2s;
}
.hover-white:hover {
  color: #fff !important;
  background: rgba(255, 255, 255, 0.05);
}
.active-link, .router-link-active {
  background: rgba(255, 255, 255, 0.1);
  box-shadow: inset 2px 0 0 #4f46e5;
}

.brand-animation {
  animation: slideInLeft 0.5s cubic-bezier(0.16, 1, 0.3, 1) both;
}
.nav-links-animation {
  animation: slideInLeft 0.5s cubic-bezier(0.16, 1, 0.3, 1) both;
  animation-delay: 0.05s;
}
.main-content-animation {
  animation: fadeIn 0.6s cubic-bezier(0.16, 1, 0.3, 1) both;
}

@keyframes slideInLeft {
  from { opacity: 0; transform: translateX(-20px); }
  to { opacity: 1; transform: translateX(0); }
}
@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}
</style>
