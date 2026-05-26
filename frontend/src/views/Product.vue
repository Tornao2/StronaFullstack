<template>
  <div class="product-page">
    <div class="container py-5">
      <div v-if="loading" class="alert alert-secondary">Ladowanie produktu...</div>
      <div v-else-if="error" class="alert alert-danger">{{ error }}</div>

      <div v-else-if="product" class="row g-4">
        <div class="col-md-5">
          <img v-if="getImage(product)" :src="getImage(product)" :alt="getName(product)"
               class="img-fluid rounded shadow-sm product-image"/>
          <div v-else class="no-image rounded shadow-sm">Brak zdjecia</div>
        </div>

        <div class="col-md-7">
          <h1 class="mb-3">{{ getName(product) }}</h1>

          <p class="mb-2">
            <strong>Cena:</strong>
            <span class="text-success fs-5 ms-2">{{ formatPrice(product) }}</span>
          </p>

          <p class="mb-2">
            <strong>Dostepnosc:</strong>
            <span v-if="getStock(product) > 0" class="text-success ms-2">{{ getStock(product) }} szt.</span>
            <span v-else class="text-danger ms-2">Brak na stanie</span>
          </p>

          <p class="mb-4">
            <strong>Opis:</strong><br/>{{ getDescription(product) }}
          </p>

          <div v-if="attributes.length" class="mb-4">
            <h4>Parametry</h4>
            <ul class="list-group">
              <li v-for="attr in attributes" :key="attr.name"
                  class="list-group-item d-flex justify-content-between">
                <span>{{ attr.name }}</span>
                <strong>{{ attr.value }}</strong>
              </li>
            </ul>
          </div>

          <transition name="fade">
            <div v-if="addedToast" class="alert alert-success py-2 mb-3">
              Dodano do koszyka!
            </div>
          </transition>

          <div class="d-flex gap-5 flex-wrap mt-2">
            <button class="btn btn-outline-success btn-lg"
                    :disabled="getStock(product) <= 0"
                    @click="handleAddToCart">
              Dodaj do koszyka
            </button>

            <button class="btn btn-success btn-lg"
                    :disabled="getStock(product) <= 0 || buyLoading"
                    @click="handleBuyNow">
              <span v-if="buyLoading" class="spinner-border spinner-border-sm me-2"/>
              {{ buyLoading ? 'Przekierowanie...' : 'Kup teraz' }}
            </button>
          </div>

          <div v-if="buyError" class="alert alert-danger mt-3 py-2">{{ buyError }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {useRoute} from 'vue-router'
import axios from 'axios'
import {useCart} from '../stores/cart.js'

const route = useRoute()
const {addToCart} = useCart()
const api = axios.create({baseURL: 'http://localhost:8080/api', withCredentials: true})

const product = ref(null)
const attributes = ref([])
const loading = ref(false)
const error = ref('')
const addedToast = ref(false)
const buyLoading = ref(false)
const buyError = ref('')

function getName(p) {
  return p.name ?? 'Brak nazwy'
}

function getDescription(p) {
  return p.description ?? 'Brak opisu'
}

function getImage(p) {
  return p.imageUrl ?? p.image_url ?? ''
}

function getStock(p) {
  return p.stockQuantity ?? p.stock_quantity ?? 0
}

function formatPrice(p) {
  const price = p.priceInGrosze ?? p.price_in_grosze ?? null
  if (price == null) return 'Brak ceny'
  return (price / 100).toFixed(2) + ' zl'
}

function handleAddToCart() {
  addToCart(product.value)
  addedToast.value = true
  setTimeout(() => {
    addedToast.value = false
  }, 2000)
}

async function handleBuyNow() {
  buyError.value = ''
  buyLoading.value = true
  try {
    const response = await api.post('/orders/create', [product.value.id])
    const orderId = response.data.orderId;

    await pollForPaymentUrl(orderId)
  } catch (err) {
    if (err.response?.status === 401) {
      buyError.value = 'Musisz byc zalogowany, aby złożyć zamowienie.'
    } else if (err.response?.data?.error === "INCOMPLETE_PROFILE") {
      buyError.value = "Dane twojego konta są niepełne. Wejdź do swojego profilu i je wypełnij, po czym ponów transakcję."
    } else {
      buyError.value = err.response?.data?.error ?? 'Wystapil blad podczas skladania zamowienia.'
    }
  } finally {
    buyLoading.value = false
  }
}

async function pollForPaymentUrl(orderId) {
  const maxAttempts = 15
  let attempts = 0

  const interval = setInterval(async () => {
    try {
      attempts++
      const response = await api.get(`/orders/${orderId}`)

      if (response.data.paymentUrl) {
        clearInterval(interval)
        window.location.href = response.data.paymentUrl
      } else if (attempts >= maxAttempts) {
        clearInterval(interval)
        buyLoading.value = false
        buyError.value = "Przekroczono czas oczekiwania na płatność."
      }
    } catch (err) {
      //clearInterval(interval)
      buyLoading.value = false
    }
  }, 2000)
}

async function loadProduct() {
  loading.value = true
  error.value = ''
  try {
    const {data} = await api.get(`/products/${route.params.id}`)
    product.value = data
    try {
      const attrRes = await api.get(`/attributes/product/${route.params.id}`)
      attributes.value = attrRes.data
    } catch {
      attributes.value = data.attributes ?? []
    }
  } catch {
    error.value = 'Nie udalo sie pobrac szczegolów produktu.'
  } finally {
    loading.value = false
  }
}

onMounted(loadProduct)
</script>

<style scoped>
.product-page {
  min-height: 100vh;
  background: #f5f7fb;
}

.product-image {
  width: 100%;
  max-height: 420px;
  object-fit: cover;
}

.no-image {
  min-height: 420px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #e9ecef;
  color: #6c757d;
  font-weight: 700;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.4s;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>