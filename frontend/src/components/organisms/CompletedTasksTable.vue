<template>
  <div class="card border-0 shadow-sm rounded-4 p-4 h-100">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h5 class="fw-bold m-0">Completed Tasks</h5>
      <AppBadge variant="success">{{ report.totalCompletedTasks || 0 }}</AppBadge>
    </div>
    <div class="table-responsive">
      <table class="table align-middle mb-0">
        <thead>
          <tr>
            <th>Task</th>
            <th>Project</th>
            <th>Assignee</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="task in report.tasks || []" :key="task.taskId">
            <td>{{ task.title }}</td>
            <td>{{ task.projectName }}</td>
            <td>{{ task.assigneeUsername }}</td>
          </tr>
          <tr v-if="!(report.tasks || []).length">
            <td colspan="3" class="text-muted">No completed tasks yet.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import AppBadge from '../atoms/AppBadge.vue'

defineProps({
  report: {
    type: Object,
    default: () => ({ tasks: [] })
  }
})
</script>
