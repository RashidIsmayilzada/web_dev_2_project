<template>
  <div class="card border-0 shadow-sm rounded-4 p-4">
    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-2">
      <div>
        <h4 class="fw-bold m-0">{{ dashboard.teamName || 'Team Dashboard' }}</h4>
        <p class="text-muted small m-0 mt-1">Cross-team productivity snapshot</p>
      </div>
      <AppBadge variant="primary">{{ dashboard.totalHours || 0 }}h</AppBadge>
    </div>

    <div class="row g-3 mb-4">
      <div class="col-md-3">
        <StatCard label="Projects" :value="dashboard.totalProjects || 0" />
      </div>
      <div class="col-md-3">
        <StatCard label="Tasks" :value="dashboard.totalTasks || 0" />
      </div>
      <div class="col-md-3">
        <StatCard label="Done" :value="dashboard.completedTasks || 0" />
      </div>
      <div class="col-md-3">
        <StatCard label="Hours" :value="`${dashboard.totalHours || 0}h`" />
      </div>
    </div>

    <div class="d-flex flex-column gap-3">
      <div
        v-for="member in dashboard.memberHours || []"
        :key="member.userId"
        class="d-flex justify-content-between align-items-center small rounded-4 border p-3"
      >
        <span class="fw-semibold">{{ member.username }}</span>
        <AppBadge variant="secondary">{{ member.hours }}h</AppBadge>
      </div>
    </div>
  </div>
</template>

<script setup>
import AppBadge from '../atoms/AppBadge.vue'
import StatCard from '../molecules/StatCard.vue'

defineProps({
  dashboard: {
    type: Object,
    default: () => ({ memberHours: [] })
  }
})
</script>
