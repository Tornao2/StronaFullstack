<template>
  <div class="orders-page">
    <div class="container py-5">
      <h1 class="mb-4">Moje zamówienia</h1>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const api = axios.create({ baseURL: 'http://localhost:8080/api', withCredentials: true })

const orders = ref([])
const loading = ref(false)
const error = ref('')

async function loadOrders() {
  loading.value = true
  error.value = ''
  try {
    const { data } = await api.get('/users/me/orders')
    orders.value = data
  } catch (err) {
    if (err.response?.status === 401) {
      router.push('/login')
    } else {
      error.value = 'Nie udało się pobrać zamówień.'
    }
  } finally {
    loading.value = false
  }
}

onMounted(loadOrders)
</script>

<style scoped>
.orders-page {
  min-height: 100vh;
  background: #f5f7fb;
}
</style>