<template>
  <div class="card border-0 shadow-sm rounded-4 p-4 h-100">
    <h5 class="fw-bold mb-4">Trends Overview</h5>
    <div class="row g-4">
      <div class="col-md-6">
        <h6 class="text-muted text-uppercase small fw-semibold">Hours</h6>
        <div v-for="point in trends.hoursTrend || []" :key="`hours-${point.label}`" class="mb-3">
          <div class="d-flex justify-content-between small mb-1">
            <span>{{ point.label }}</span>
            <span>{{ point.value }}h</span>
          </div>
          <div class="progress" style="height: 8px;">
            <div class="progress-bar bg-primary" :style="{ width: `${Math.min(point.value * 10, 100)}%` }"></div>
          </div>
        </div>
      </div>
      <div class="col-md-6">
        <h6 class="text-muted text-uppercase small fw-semibold">Completed Tasks</h6>
        <div v-for="point in trends.completedTasksTrend || []" :key="`done-${point.label}`" class="mb-3">
          <div class="d-flex justify-content-between small mb-1">
            <span>{{ point.label }}</span>
            <span>{{ point.value }}</span>
          </div>
          <div class="progress" style="height: 8px;">
            <div class="progress-bar bg-success" :style="{ width: `${Math.min(point.value * 10, 100)}%` }"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  trends: {
    type: Object,
    default: () => ({ hoursTrend: [], completedTasksTrend: [] })
  }
})
</script>
