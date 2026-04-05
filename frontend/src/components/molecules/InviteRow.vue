<template>
  <div class="d-flex justify-content-between align-items-center gap-3 p-3 rounded-4 border invite-row">
    <div>
      <h6 class="m-0 fw-bold">{{ invite.invitedUsername }}</h6>
      <p class="m-0 text-muted small">Invited by {{ invite.invitedByUsername }}</p>
    </div>
    <AppBadge :variant="badgeVariant">{{ invite.status }}</AppBadge>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import AppBadge from '../atoms/AppBadge.vue'

const props = defineProps({
  invite: {
    type: Object,
    required: true
  }
})

const badgeVariant = computed(() => {
  if (props.invite.status === 'ACCEPTED') return 'success'
  if (props.invite.status === 'DECLINED') return 'danger'
  return 'warning'
})
</script>

<style scoped>
.invite-row {
  background: #fff;
}
</style>
