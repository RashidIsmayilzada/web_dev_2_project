<template>
  <div class="card border-0 shadow-sm rounded-4 h-100 p-4 stat-card">
    <p class="text-uppercase text-muted small fw-semibold mb-2">{{ label }}</p>
    <h2 class="fw-bold text-dark m-0">{{ value }}</h2>
    <p v-if="hint" class="small text-muted mt-2 mb-0">{{ hint }}</p>
  </div>
</template>

<script setup>
defineProps({
  label: String,
  value: [String, Number],
  hint: String
})
</script>

<style scoped>
.stat-card {
  background: linear-gradient(180deg, #ffffff, #f7fafc);
}
</style>
