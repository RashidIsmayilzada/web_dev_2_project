<template>
  <div class="card border-0 shadow-sm rounded-4 p-4 h-100">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h5 class="fw-bold m-0">Members</h5>
      <AppBadge variant="secondary">{{ members.length }}</AppBadge>
    </div>
    <div class="d-flex flex-column gap-3">
      <MemberRow
        v-for="member in members"
        :key="member.userId"
        :member="member"
        :can-manage="canManage"
        :can-remove="canManage && member.role !== 'OWNER'"
        @role-change="emit('role-change', $event)"
        @remove="$emit('remove', $event)"
      />
      <div v-if="members.length === 0" class="text-muted small">No team members yet.</div>
    </div>
  </div>
</template>

<script setup>
import AppBadge from '../atoms/AppBadge.vue'
import MemberRow from '../molecules/MemberRow.vue'

defineProps({
  members: {
    type: Array,
    default: () => []
  },
  canManage: Boolean
})

const emit = defineEmits(['role-change', 'remove'])
</script>
