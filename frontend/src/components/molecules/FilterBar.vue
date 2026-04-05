<template>
  <div class="card border-0 shadow-sm rounded-4 p-3 filter-bar">
    <div class="row g-3 align-items-end">
      <div class="col-md-3" v-for="field in fields" :key="field.key">
        <FormGroup :label="field.label" class="mb-0">
          <AppInput
            v-if="field.type !== 'select'"
            :model-value="modelValue[field.key] ?? ''"
            :placeholder="field.placeholder"
            @update:model-value="updateField(field.key, $event)"
          />
          <AppSelect
            v-else
            :model-value="modelValue[field.key] ?? ''"
            @update:model-value="updateField(field.key, $event)"
          >
            <option value="">{{ field.placeholder || 'All' }}</option>
            <option v-for="option in field.options" :key="option.value" :value="option.value">
              {{ option.label }}
            </option>
          </AppSelect>
        </FormGroup>
      </div>

      <div class="col-md-3 d-flex gap-2">
        <AppButton variant="primary" class="w-100" @click="$emit('apply')">Apply</AppButton>
        <AppButton variant="light" class="w-100" @click="$emit('reset')">Reset</AppButton>
      </div>
    </div>
  </div>
</template>

<script setup>
import FormGroup from './FormGroup.vue'
import AppInput from '../atoms/AppInput.vue'
import AppSelect from '../atoms/AppSelect.vue'
import AppButton from '../atoms/AppButton.vue'

const props = defineProps({
  modelValue: {
    type: Object,
    required: true
  },
  fields: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['update:modelValue', 'apply', 'reset'])

const updateField = (key, value) => {
  emit('update:modelValue', {
    ...props.modelValue,
    [key]: value
  })
}
</script>

<style scoped>
.filter-bar {
  background: linear-gradient(180deg, #ffffff, #f9fbff);
}
</style>
