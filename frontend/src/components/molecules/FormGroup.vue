<template>
  <div class="mb-3 text-start">
    <label class="form-label fw-semibold text-white mb-2" v-if="label" style="letter-spacing: 0.5px;">{{ label }}</label>
    <slot />
  </div>
</template>

<script setup>
defineProps({
  label: String
})
</script>
