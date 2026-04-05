<template>
  <AuthLayout>
    <div class="mb-4 header-animation">
      <h2 class="fw-bold mb-1 text-white">Welcome Back</h2>
      <p class="text-white-50 small">Log in to Team Productivity Hub</p>
    </div>
    <form @submit.prevent="handleLogin" class="form-animation">
      <FormGroup label="Username">
        <AppInput v-model="username" placeholder="Enter your username" />
      </FormGroup>
      <FormGroup label="Password">
        <AppInput v-model="password" type="password" placeholder="••••••••" />
      </FormGroup>
      
      <AppButton type="submit" variant="primary" class="w-100 mt-4 rounded-3 py-2" @click="handleLogin">Sign In</AppButton>
      
      <div v-if="error" class="text-danger mt-3 small bg-danger bg-opacity-10 py-2 rounded">
        {{ error }}
      </div>

      <p class="mt-4 mb-0 small text-white-50">
        Need an account?
        <RouterLink to="/register" class="auth-link">Create one</RouterLink>
      </p>
    </form>
  </AuthLayout>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { RouterLink } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import AuthLayout from '../components/templates/AuthLayout.vue'
import FormGroup from '../components/molecules/FormGroup.vue'
import AppInput from '../components/atoms/AppInput.vue'
import AppButton from '../components/atoms/AppButton.vue'

const username = ref('')
const password = ref('')
const error = ref('')

const router = useRouter()
const authStore = useAuthStore()

const handleLogin = async () => {
  try {
    error.value = ''
    if (!username.value.trim() || !password.value) {
      error.value = 'Please enter your username and password'
      return
    }
    await authStore.login({ username: username.value, password: password.value })
    router.push('/dashboard')
  } catch (err) {
    error.value = err.response?.data?.message || 'Invalid username or password'
  }
}
</script>

<style scoped>
:deep(.form-label) {
  color: #ffffff !important;
}

.header-animation {
  animation: fadeInDown 0.6s cubic-bezier(0.16, 1, 0.3, 1) both;
  animation-delay: 0.1s;
}
.form-animation {
  animation: fadeInUp 0.6s cubic-bezier(0.16, 1, 0.3, 1) both;
  animation-delay: 0.2s;
}

.auth-link {
  color: #c4b5fd;
  text-decoration: none;
  font-weight: 600;
}

.auth-link:hover {
  color: #ddd6fe;
  text-decoration: underline;
}

@keyframes fadeInDown {
  from { opacity: 0; transform: translateY(-20px); }
  to { opacity: 1; transform: translateY(0); }
}
@keyframes fadeInUp {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>
