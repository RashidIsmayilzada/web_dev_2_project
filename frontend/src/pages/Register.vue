<template>
  <AuthLayout>
    <div class="mb-4 header-animation">
      <h2 class="fw-bold mb-1 text-white">Create Account</h2>
      <p class="text-white-50 small">Join Team Productivity Hub</p>
    </div>

    <form @submit.prevent="handleRegister" class="form-animation">
      <FormGroup label="Username">
        <AppInput v-model="username" placeholder="Choose a username" />
      </FormGroup>

      <FormGroup label="Email">
        <AppInput v-model="email" type="email" placeholder="name@example.com" />
      </FormGroup>

      <FormGroup label="Password">
        <AppInput v-model="password" type="password" placeholder="At least 8 characters" />
      </FormGroup>

      <FormGroup label="Confirm Password">
        <AppInput v-model="confirmPassword" type="password" placeholder="Re-enter your password" />
      </FormGroup>

      <AppButton type="submit" variant="primary" class="w-100 mt-4 rounded-3 py-2" @click="handleRegister">Create Account</AppButton>

      <div v-if="error" class="text-danger mt-3 small bg-danger bg-opacity-10 py-2 rounded">
        {{ error }}
      </div>

      <p class="mt-4 mb-0 small text-white-50">
        Already have an account?
        <RouterLink to="/login" class="auth-link">Sign in</RouterLink>
      </p>
    </form>
  </AuthLayout>
</template>

<script setup>
import { ref } from 'vue'
import { RouterLink, useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import AuthLayout from '../components/templates/AuthLayout.vue'
import FormGroup from '../components/molecules/FormGroup.vue'
import AppInput from '../components/atoms/AppInput.vue'
import AppButton from '../components/atoms/AppButton.vue'

const username = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const error = ref('')

const router = useRouter()
const authStore = useAuthStore()

const handleRegister = async () => {
  try {
    error.value = ''

    if (!username.value.trim() || !email.value.trim() || !password.value || !confirmPassword.value) {
      error.value = 'Please fill in all fields'
      return
    }

    if (password.value !== confirmPassword.value) {
      error.value = 'Passwords do not match'
      return
    }

    await authStore.register({
      username: username.value,
      email: email.value,
      password: password.value
    })

    router.push('/dashboard')
  } catch (err) {
    const response = err.response?.data
    error.value = response?.message || 'Could not create your account'
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
