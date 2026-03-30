<template>
  <div class="products-page">
    <div class="container py-5">
      <h1 class="mb-4">{{ pageTitle }}</h1>

      <div v-if="loading" class="alert alert-secondary">
        Ladowanie produktow...
      </div>

      <div v-else-if="error" class="alert alert-danger">
        {{ error }}
      </div>

      <div v-else-if="filteredProducts.length === 0" class="alert alert-warning">
        {{ emptyMessage }}
      </div>
<!--odstep miedzy produktami -góra i dół-->
      <div v-else class="row g-4" style="row-gap: 2rem;">
        <div
            v-for="product in filteredProducts"
            :key="product.id"
            class="col-9 col-md-6 col-xl-4"
        >
          <router-link :to="`/product/${product.id}`" class="card h-100 shadow-sm border-0 product-card-link">
            <div v-if="product.imageUrl || product.image_url">
              <img
                  :src="product.imageUrl || product.image_url"
                  :alt="product.name"
                  class="card-img-top product-image"
              />
            </div>
            <div v-else class="no-image">
              Brak zdjecia
            </div>

            <div class="card-body d-flex flex-column">
              <h5 class="card-title">{{ product.name }}</h5>

              <p class="card-text text-muted">
                {{ shortText(product.description) }}
              </p>

              <p class="mb-2">
                <strong>{{ formatPrice(product) }}</strong>
              </p>

              <p class="mb-0">
                Dostepnosc:
                <span v-if="getStock(product) > 0">{{ getStock(product) }} szt.</span>
                <span v-else class="text-danger">Brak na stanie</span>
              </p>
            </div>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'

const route = useRoute()

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true,
})

const products = ref([])
const categories = ref([])
const loading = ref(false)
const error = ref('')

const searchQuery = computed(() => (route.query.q || '').toString().trim())

const categoryNameMap = {
  '/laptops':      'Laptopy',
  '/cpus':         'Procesory',
  '/gpus':         'Karty graficzne',
  '/ram':          'RAM',
  '/motherboards': 'Plyty glowne',
  '/supplies':     'Zasilacze',
  '/cooling':      'Chlodzenia',
  '/preassembled': 'Gotowe stacje',
}

const pageTitle = computed(() => {
  if (route.path === '/search') {
    return searchQuery.value ? `Wyniki dla: "${searchQuery.value}"` : 'Wyszukiwarka'
  }
  return categoryNameMap[route.path] || 'Produkty'
})

const emptyMessage = computed(() =>
    route.path === '/search'
        ? 'Nie znaleziono produktow o takiej nazwie.'
        : 'Brak produktow w tej kategorii.'
)

function normalize(text) {
  return (text || '').toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, '').trim()
}

const filteredProducts = computed(() => {
  if (!searchQuery.value) return products.value
  const q = normalize(searchQuery.value)
  return products.value.filter(p => normalize(p.name).includes(q))
})

function shortText(text) {
  if (!text) return 'Brak opisu produktu.'
  return text.length > 100 ? text.slice(0, 100) + '...' : text
}

function getStock(product) {
  return product.stockQuantity ?? product.stock_quantity ?? 0
}

function formatPrice(product) {
  const price = product.priceInGrosze ?? product.price_in_grosze ?? null
  if (price == null) return 'Brak ceny'
  return (price / 100).toFixed(2) + ' zl'
}

async function loadCategories() {
  const { data } = await api.get('/categories')
  categories.value = data
}

function getCategoryIdFromRoute() {
  const wantedName = categoryNameMap[route.path]
  if (!wantedName) return null
  const cat = categories.value.find(c => normalize(c.name) === normalize(wantedName))
  return cat ? cat.id : null
}

async function loadProducts() {
  loading.value = true
  error.value = ''
  try {
    await loadCategories()

    if (route.path === '/search') {
      const { data } = await api.get('/products')
      products.value = data
    } else if (categoryNameMap[route.path]) {
      const categoryId = getCategoryIdFromRoute()
      if (!categoryId) {
        products.value = []
        return
      }
      const { data } = await api.get(`/products/category/${categoryId}`)
      products.value = data
    } else {
      products.value = []
    }
  } catch (err) {
    console.error(err)
    error.value = 'Nie udalo sie pobrac produktow.'
  } finally {
    loading.value = false
  }
}

onMounted(loadProducts)
watch(() => route.fullPath, loadProducts)
</script>

<style scoped>
.products-page {
  min-height: 100vh;
  background: #f5f7fb;
  padding: 40px 40px 40px;
}

.product-card-link {
  display: block;
  text-decoration: none;
  color: inherit;
  cursor: pointer;
  transition: transform 0.15s, box-shadow 0.15s;
  border-radius: 12px;
  overflow: hidden;
}

.product-card-link:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 24px rgba(0,0,0,0.12) !important;
  color: inherit;
}

.product-image {
  width: 100%;
  height: 240px;
  object-fit: cover;
}

.no-image {
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #e9ecef;
  color: #6c757d;
  font-weight: 600;
}
</style>