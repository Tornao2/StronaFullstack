<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const products = ref([])

const fetchProducts = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/products')
    products.value = response.data
  } catch (error) {
    console.error("Błąd pobierania danych:", error)
  }
}
onMounted(fetchProducts)
</script>

<template>
  <div class="container">
    <h1>Moja Uczelnia - Sklep</h1>   
    <div v-if="products.length === 0">Ładowanie produktów lub brak danych...</div>
    <ul v-else>
      <li v-for="product in products" :key="product.id">
        <strong>{{ product.name }}</strong> - {{ product.price }} zł
        <p>{{ product.description }}</p>
      </li>
    </ul>
  </div>
</template>

<style>
.container { padding: 20px; font-family: sans-serif; }
li { margin-bottom: 20px; border-bottom: 1px solid #ccc; list-style: none; }
</style>