<template>
  <div class="card border-0 shadow-sm rounded-4 p-4 h-100">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h5 class="fw-bold m-0">Invitations</h5>
      <AppBadge variant="warning">{{ invites.length }}</AppBadge>
    </div>

    <div v-if="canInvite" class="mb-4">
      <FormGroup label="Invite By Username" class="mb-2">
        <AppInput v-model="inviteUsername" placeholder="Enter an existing username" />
      </FormGroup>
      <AppButton variant="primary" @click="submitInvite" :disabled="!inviteUsername.trim()">Send Invite</AppButton>
    </div>

    <div class="d-flex flex-column gap-3">
      <InviteRow v-for="invite in invites" :key="invite.id" :invite="invite" />
      <div v-if="invites.length === 0" class="text-muted small">No invitations yet.</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import AppBadge from '../atoms/AppBadge.vue'
import AppButton from '../atoms/AppButton.vue'
import AppInput from '../atoms/AppInput.vue'
import FormGroup from '../molecules/FormGroup.vue'
import InviteRow from '../molecules/InviteRow.vue'

const props = defineProps({
  invites: {
    type: Array,
    default: () => []
  },
  canInvite: Boolean
})

const emit = defineEmits(['invite'])
const inviteUsername = ref('')

const submitInvite = () => {
  emit('invite', inviteUsername.value.trim())
  inviteUsername.value = ''
}
</script>
