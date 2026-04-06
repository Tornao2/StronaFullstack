<template>
  <transition name="slide">
    <div v-if="isOpen" class="cart-overlay" @click.self="$emit('close')">
      <div class="cart-sidebar">
        <div class="cart-header">
          <h5 class="mb-0">Koszyk <span class="badge bg-danger">{{ totalCount }}</span></h5>          <button class="close-btn" @click="$emit('close')">✕</button>
        </div>
        <div class="cart-body">
          <div v-if="items.length === 0" class="empty-cart">
            <p>Twoj koszyk jest pusty.</p>
          </div>
          <div v-else>
            <div v-for="item in items" :key="item.id" class="cart-item">
              <img v-if="item.imageUrl" :src="item.imageUrl" :alt="item.name" class="cart-item-img" />
              <div v-else class="cart-item-img no-img" />
              <div class="cart-item-info">
                <p class="cart-item-name">{{ item.name }}</p>
                <p class="cart-item-price">{{ formatPrice(item.priceInGrosze) }} / szt.</p>
                <div class="qty-controls">
                  <button class="btn btn-sm btn-outline-secondary" @click="changeQuantity(item.id, -1)">-</button>
                  <span class="qty-value">{{ item.quantity }}</span>
                  <button class="btn btn-sm btn-outline-secondary" @click="changeQuantity(item.id, +1)">+</button>
                  <button class="btn btn-sm btn-outline-danger ms-2" @click="removeFromCart(item.id)">x</button>
                </div>
              </div>
              <div class="cart-item-total">{{ formatPrice(item.priceInGrosze * item.quantity) }}</div>
            </div>
          </div>
        </div>
        <div v-if="items.length > 0" class="cart-footer">
          <div class="d-flex justify-content-between mb-3">
            <strong>Razem:</strong>
            <strong class="text-success fs-5">{{ formatPrice(totalPrice) }}</strong>
          </div>
          <div v-if="checkoutError" class="alert alert-danger py-2 mb-2 small">{{ checkoutError }}</div>
          <button class="btn btn-success w-100 btn-lg" :disabled="checkoutLoading" @click="checkout">
            <span v-if="checkoutLoading" class="spinner-border spinner-border-sm me-2" />
            {{ checkoutLoading ? 'Przekierowanie...' : 'Przejdz do platnosci' }}
          </button>
          <button class="btn btn-outline-secondary w-100 mt-2" @click="clearCart">Wyczysc koszyk</button>
        </div>
      </div>
    </div>
  </transition>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useCart } from '../stores/cart.js'

defineProps({ isOpen: Boolean })
defineEmits(['close'])

const { items, removeFromCart, changeQuantity, clearCart, totalCount, totalPrice, toOrderPayload } = useCart()
const checkoutLoading = ref(false)
const checkoutError = ref('')
const api = axios.create({ baseURL: 'http://localhost:8080/api', withCredentials: true })

function formatPrice(grosze) {
  return (grosze / 100).toFixed(2) + ' zl'
}

async function checkout() {
  checkoutError.value = ''
  checkoutLoading.value = true
  try {
    const { data } = await api.post('/orders/create', toOrderPayload())
    if (data.paymentUrl) window.location.href = data.paymentUrl
  } catch (err) {
    if (err.response?.status === 401) {
      checkoutError.value = 'Musisz byc zalogowany, aby zlozyc zamowienie.'
    } else {
      checkoutError.value = err.response?.data?.error ?? 'Wystapil blad podczas skladania zamowienia.'
    }
  } finally {
    checkoutLoading.value = false
  }
}
</script>

<style scoped>
.cart-overlay { position: fixed; inset: 0; background: rgba(0,0,0,0.45); z-index: 1050; display: flex; justify-content: flex-end; }
.cart-sidebar { width: 420px; max-width: 100vw; background: #fff; display: flex; flex-direction: column; height: 100%; box-shadow: -4px 0 20px rgba(0,0,0,0.15); }
.cart-header { background: #212529; color: #fff; padding: 16px 20px; display: flex; justify-content: space-between; align-items: center; }
.cart-body { flex: 1; overflow-y: auto; padding: 16px; }
.empty-cart { text-align: center; margin-top: 60px; color: #888; font-size: 1.1rem; }
.cart-item { display: flex; align-items: flex-start; gap: 12px; padding: 12px 0; border-bottom: 1px solid #f0f0f0; }
.cart-item-img { width: 64px; height: 64px; object-fit: cover; border-radius: 6px; flex-shrink: 0; }
.no-img { background: #e9ecef; }
.cart-item-info { flex: 1; min-width: 0; }
.cart-item-name { font-weight: 600; margin: 0 0 2px; font-size: 0.9rem; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.cart-item-price { font-size: 0.82rem; color: #888; margin: 0 0 6px; }
.qty-controls { display: flex; align-items: center; gap: 6px; }
.qty-value { min-width: 24px; text-align: center; font-weight: 600; }
.cart-item-total { font-weight: 700; color: #198754; white-space: nowrap; padding-top: 4px; }
.cart-footer { padding: 16px 20px; border-top: 2px solid #f0f0f0; background: #fafafa; }
.slide-enter-active, .slide-leave-active { transition: opacity 0.25s ease; }
.slide-enter-from, .slide-leave-to { opacity: 0; }
.close-btn {
  background: #e3342f;
  border: none;
  border-radius: 6px;
  color: #fff;
  width: 32px;
  height: 32px;
  font-size: 1rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-btn:hover {
  background: #cc2a26;
}
</style>