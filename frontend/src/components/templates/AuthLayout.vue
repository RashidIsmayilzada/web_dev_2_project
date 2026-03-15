<template>
  <div class="d-flex justify-content-center align-items-center vh-100 bg-animated">
    <div class="glass-card p-5 text-center shadow-lg">
      <slot />
    </div>
  </div>
</template>

<style scoped>
.bg-animated {
  background: linear-gradient(-45deg, #1e1e2f, #2a2a40, #16213e, #0f3460);
  background-size: 400% 400%;
  animation: gradientBG 15s ease infinite;
}

@keyframes gradientBG {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

.glass-card {
  width: 100%;
  max-width: 420px;
  background: rgba(25, 25, 35, 0.65);
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  color: white;
  animation: slideUp 0.6s cubic-bezier(0.16, 1, 0.3, 1);
}

@keyframes slideUp {
  from { opacity: 0; transform: translateY(40px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>
