<template>
  <div class="card border-0 shadow-sm rounded-4 p-4 h-100">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h5 class="fw-bold m-0">Weekly Summary</h5>
      <AppBadge variant="success">{{ summary.totalHours || 0 }}h</AppBadge>
    </div>
    <div class="d-flex flex-column gap-3">
      <div v-for="item in summary.dailyHours || []" :key="item.day">
        <div class="d-flex justify-content-between small mb-1">
          <span>{{ item.day }}</span>
          <span>{{ item.hours }}h</span>
        </div>
        <div class="progress" style="height: 8px;">
          <div class="progress-bar" :style="{ width: `${Math.min((item.hours || 0) * 10, 100)}%` }"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import AppBadge from '../atoms/AppBadge.vue'

defineProps({
  summary: {
    type: Object,
    default: () => ({ dailyHours: [] })
  }
})
</script>
