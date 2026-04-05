<template>
  <input 
    :type="type" 
    class="form-control custom-input" 
    :value="modelValue" 
    @input="$emit('update:modelValue', $event.target.value)" 
    :placeholder="placeholder" 
    :disabled="disabled"
  />
</template>

<script setup>
defineProps({
  modelValue: String,
  type: { type: String, default: 'text' },
  placeholder: String,
  disabled: Boolean
})
</script>

<style scoped>
/* Base styling for default bootstrap inputs */
.custom-input {
  border-radius: 8px;
  transition: all 0.3s ease;
}

:global(.glass-container .custom-input) {
  border: 1px solid rgba(255, 255, 255, 0.2);
  background: rgba(255, 255, 255, 0.1);
  color: #fff;
}
:global(.glass-container .custom-input::placeholder) {
  color: rgba(255, 255, 255, 0.6);
}
:global(.glass-container .custom-input:focus) {
  background: rgba(255, 255, 255, 0.2);
  border-color: #0d6efd;
  box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
  color: #fff;
}
</style>
