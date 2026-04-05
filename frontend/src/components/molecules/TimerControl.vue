<template>
  <div class="card border-0 shadow-sm rounded-4 p-4 timer-card">
    <div class="d-flex justify-content-between align-items-start gap-3 flex-wrap">
      <div>
        <p class="text-uppercase text-muted small fw-semibold mb-2">Active Timer</p>
        <h5 class="fw-bold mb-1">{{ activeTimer ? activeTimer.taskTitle : 'No active timer' }}</h5>
        <p class="text-muted small mb-0">
          {{ activeTimer ? `Started ${formatStart(activeTimer.startedAt)}` : 'Start a timer from the Tasks page.' }}
        </p>
      </div>
      <div class="d-flex gap-2">
        <AppButton v-if="activeTimer" variant="success" @click="$emit('stop')">Stop</AppButton>
        <AppButton v-if="activeTimer" variant="light" @click="$emit('cancel')">Cancel</AppButton>
      </div>
    </div>
  </div>
</template>

<script setup>
import AppButton from '../atoms/AppButton.vue'

defineProps({
  activeTimer: Object
})

defineEmits(['stop', 'cancel'])

const formatStart = (value) => {
  if (!value) return ''
  return new Date(value).toLocaleString([], { dateStyle: 'medium', timeStyle: 'short' })
}
</script>

<style scoped>
.timer-card {
  background: linear-gradient(145deg, #111827, #1f2937);
  color: #fff;
}

.timer-card .text-muted {
  color: rgba(255, 255, 255, 0.75) !important;
}
</style>
