<template>
  <select
    class="form-select custom-select"
    :value="modelValue"
    :disabled="disabled"
    @change="$emit('update:modelValue', $event.target.value)"
  >
    <slot />
  </select>
</template>

<script setup>
defineProps({
  modelValue: [String, Number],
  disabled: Boolean
})
</script>

<style scoped>
.custom-select {
  border-radius: 10px;
}
</style>
