<template>
  <button :class="['btn', variantClass, 'custom-btn']" @click="$emit('click')">
    <slot />
  </button>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  variant: {
    type: String,
    default: 'primary' // success, danger, warning
  }
})

const variantClass = computed(() => `btn-${props.variant}`)
</script>

<style scoped>
.btn.custom-btn {
  font-weight: 600;
  letter-spacing: 0.5px;
  transition: transform 0.2s cubic-bezier(0.4, 0, 0.2, 1), box-shadow 0.2s;
  border-radius: 8px;
}
.btn.custom-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}
.btn-primary.custom-btn {
  background: linear-gradient(90deg, #4f46e5, #7c3aed);
  border: none;
  box-shadow: 0 4px 12px rgba(124, 58, 237, 0.25);
}
.btn-primary.custom-btn:hover {
  box-shadow: 0 6px 16px rgba(124, 58, 237, 0.4);
}
</style>
