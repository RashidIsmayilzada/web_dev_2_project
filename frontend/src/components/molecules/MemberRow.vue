<template>
  <div class="d-flex justify-content-between align-items-center gap-3 p-3 rounded-4 border member-row flex-wrap">
    <div>
      <h6 class="m-0 fw-bold">{{ member.username }}</h6>
      <p class="m-0 text-muted small">{{ member.email }}</p>
    </div>

    <div class="d-flex align-items-center gap-2">
      <AppSelect
        v-if="canManage"
        :model-value="member.role"
        @update:model-value="$emit('role-change', { member, role: $event })"
      >
        <option value="OWNER">OWNER</option>
        <option value="MANAGER">MANAGER</option>
        <option value="MEMBER">MEMBER</option>
      </AppSelect>
      <AppBadge v-else variant="dark">{{ member.role }}</AppBadge>
      <AppButton v-if="canRemove" variant="danger" @click="$emit('remove', member)">Remove</AppButton>
    </div>
  </div>
</template>

<script setup>
import AppBadge from '../atoms/AppBadge.vue'
import AppButton from '../atoms/AppButton.vue'
import AppSelect from '../atoms/AppSelect.vue'

defineProps({
  member: {
    type: Object,
    required: true
  },
  canManage: Boolean,
  canRemove: Boolean
})

defineEmits(['role-change', 'remove'])
</script>

<style scoped>
.member-row {
  background: #fff;
}
</style>
