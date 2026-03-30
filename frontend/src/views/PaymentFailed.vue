<template>
  <div class="result-page">
    <div class="result-card">
      <div class="icon success-icon">✓</div>
      <h1>Płatność zakończona!</h1>
      <p class="subtitle">Twoje zamówienie <strong>#{{ orderId }}</strong> zostało przyjęte. Wysłaliśmy potwierdzenie na Twój adres e-mail.</p>
      <div class="actions">
        <router-link to="/me/orders" class="btn btn-success btn-lg">Zobacz zamówienia</router-link>
        <router-link to="/home" class="btn btn-outline-secondary btn-lg">Wróć do sklepu</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const orderId = computed(() => route.query.orderId ?? '—')
</script>

<style scoped>
.result-page {
  min-height: 80vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fb;
  padding: 40px 20px;
}

.result-card {
  background: #fff;
  border-radius: 16px;
  padding: 56px 48px;
  text-align: center;
  max-width: 480px;
  width: 100%;
  box-shadow: 0 4px 24px rgba(0,0,0,0.07);
}

.icon {
  width: 72px;
  height: 72px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
  margin: 0 auto 24px;
}

.success-icon {
  background: #d1fae5;
  color: #065f46;
}

h1 {
  font-size: 1.6rem;
  font-weight: 600;
  margin-bottom: 12px;
  color: #1a1a1a;
}

.subtitle {
  color: #6b7280;
  line-height: 1.6;
  margin-bottom: 32px;
}

.actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>