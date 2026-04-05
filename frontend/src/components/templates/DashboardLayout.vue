<template>
  <div class="dashboard-shell min-vh-100 bg-light-fade">
    <div
      v-if="isMobileNavOpen"
      class="sidebar-backdrop"
      @click="closeMobileNav"
    ></div>

    <div class="mobile-topbar d-lg-none">
      <button type="button" class="btn btn-dark rounded-pill px-3" @click="toggleMobileNav">
        <i class="bi bi-list me-2"></i>Menu
      </button>
      <div class="fw-bold text-dark">ProductivityHub</div>
    </div>

    <!-- Sidebar -->
    <div class="sidebar text-white p-4 d-flex flex-column shadow-lg" :class="{ 'sidebar-open': isMobileNavOpen }">
      <div class="mb-5 d-flex align-items-center gap-2 brand-animation">
        <div class="rounded-circle bg-primary bg-gradient p-2"></div>
        <h4 class="fw-bold m-0" style="letter-spacing: 1px;">ProductivityHub</h4>
      </div>
      
      <ul class="nav flex-column gap-3 mb-auto nav-links-animation">
        <li class="nav-item">
          <router-link to="/dashboard" class="nav-link text-white rounded-3 px-3 py-2 fw-semibold transition-all" @click="closeMobileNav">
            <i class="bi bi-grid-fill me-2"></i> Dashboard
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/teams" class="nav-link text-white rounded-3 px-3 py-2 fw-semibold transition-all" @click="closeMobileNav">
            <i class="bi bi-people-fill me-2"></i> Teams
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/projects" class="nav-link text-white rounded-3 px-3 py-2 fw-semibold transition-all" @click="closeMobileNav">
            <i class="bi bi-folder-fill me-2"></i> Projects
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/tasks" class="nav-link text-white rounded-3 px-3 py-2 fw-semibold transition-all" @click="closeMobileNav">
            <i class="bi bi-check-square-fill me-2"></i> Tasks
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/timelogs" class="nav-link text-white rounded-3 px-3 py-2 fw-semibold transition-all" @click="closeMobileNav">
            <i class="bi bi-clock-history me-2"></i> Time Logs
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/reports" class="nav-link text-white rounded-3 px-3 py-2 fw-semibold transition-all" @click="closeMobileNav">
            <i class="bi bi-bar-chart-fill me-2"></i> Reports
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/team-dashboard" class="nav-link text-white rounded-3 px-3 py-2 fw-semibold transition-all" @click="closeMobileNav">
            <i class="bi bi-kanban-fill me-2"></i> Team Dashboard
          </router-link>
        </li>
      </ul>
      
      <div class="mt-auto nav-links-animation" style="animation-delay: 0.1s;">
        <div class="user-card rounded-4 px-3 py-3 mb-3">
          <p class="text-uppercase small text-white-50 mb-1 user-label">Signed In As</p>
          <div class="fw-semibold text-white d-flex align-items-center gap-2">
            <i class="bi bi-person-circle"></i>
            <span>{{ authStore.user?.username || 'Unknown User' }}</span>
          </div>
        </div>
        <hr class="border-white border-opacity-25" />
        <a href="#" @click.prevent="logout" class="nav-link text-white-50 hover-white rounded-3 px-3 py-2 transition-all">
          <i class="bi bi-box-arrow-right me-2"></i> Logout
        </a>
      </div>
    </div>
    
    <!-- Main Content -->
    <div class="main-content flex-grow-1 p-5 main-content-animation overflow-auto">
      <slot />
    </div>
  </div>
</template>

<script setup>
import { onBeforeUnmount, onMounted, ref } from 'vue'
import { useAuthStore } from '../../stores/authStore.js'
import { useRouter } from 'vue-router'
const authStore = useAuthStore()
const router = useRouter()
const isMobileNavOpen = ref(false)

const closeMobileNav = () => {
  isMobileNavOpen.value = false
}

const syncMobileNav = () => {
  if (window.innerWidth >= 992) {
    closeMobileNav()
  }
}

const toggleMobileNav = () => {
  isMobileNavOpen.value = !isMobileNavOpen.value
}

const logout = () => {
  closeMobileNav()
  authStore.logout()
  router.push('/login')
}

onMounted(() => {
  window.addEventListener('resize', syncMobileNav)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', syncMobileNav)
})
</script>

<style scoped>
.dashboard-shell {
  display: flex;
  min-height: 100vh;
}

.bg-light-fade {
  background: #f8f9fa;
}

.mobile-topbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1025;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.25rem;
  background: rgba(248, 249, 250, 0.96);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(17, 24, 39, 0.08);
}

.sidebar {
  width: 280px;
  background: linear-gradient(180deg, #111827, #1f2937);
  flex-shrink: 0;
}

.transition-all {
  transition: all 0.2s;
}
.hover-white:hover {
  color: #fff !important;
  background: rgba(255, 255, 255, 0.05);
}
.user-card {
  background: rgba(255, 255, 255, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.08);
}
.user-label {
  letter-spacing: 0.12em;
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

.main-content {
  min-width: 0;
}

.sidebar-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(15, 23, 42, 0.45);
  z-index: 1029;
}

@keyframes slideInLeft {
  from { opacity: 0; transform: translateX(-20px); }
  to { opacity: 1; transform: translateX(0); }
}
@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@media (max-width: 991.98px) {
  .dashboard-shell {
    display: block;
  }

  .sidebar {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    z-index: 1030;
    max-width: 86vw;
    transform: translateX(-100%);
    transition: transform 0.25s ease;
    overflow-y: auto;
  }

  .sidebar-open {
    transform: translateX(0);
  }

  .main-content {
    padding: 5.75rem 1rem 1.5rem !important;
  }
}

@media (min-width: 992px) {
  .mobile-topbar {
    display: none;
  }
}
</style>
