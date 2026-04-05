<template>
  <span class="badge rounded-pill px-3 py-2" :class="variantClass">
    <slot />
  </span>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  variant: {
    type: String,
    default: 'secondary'
  }
})

const variantClass = computed(() => {
  const classes = {
    primary: 'bg-primary-subtle text-primary-emphasis',
    success: 'bg-success-subtle text-success-emphasis',
    warning: 'bg-warning-subtle text-warning-emphasis',
    danger: 'bg-danger-subtle text-danger-emphasis',
    dark: 'bg-dark-subtle text-dark-emphasis',
    secondary: 'bg-secondary-subtle text-secondary-emphasis'
  }

  return classes[props.variant] || classes.secondary
})
</script>
