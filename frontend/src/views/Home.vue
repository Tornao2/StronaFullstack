<template>
  <div class="home-page">

    <!-- Banner reklamowy -->
    <section class="container py-5">
      <router-link to="/cpus?brand=intel" class="banner-link">
        <img src="/reklama_intel.png" alt="Reklama Intel Core" class="banner-img" />
      </router-link>
    </section>

    <!-- Nowości -->
    <section class="container pb-5">
      <h2 class="section-title mb-4">Nowości</h2>

      <div v-if="loading" class="alert alert-secondary">Ladowanie produktow...</div>
      <div v-else-if="error" class="alert alert-danger">{{ error }}</div>
      <div v-else-if="newest.length === 0" class="alert alert-warning">Brak produktow do wyswietlenia.</div>

      <div v-else class="row g-4" style="row-gap: 2rem;">
        <div v-for="product in newest" :key="product.id" class="col-12 col-sm-6 col-lg-4">
          <router-link :to="`/product/${product.id}`" class="card h-100 shadow-sm border-0 product-card-link">
            <img
                v-if="product.imageUrl"
                :src="product.imageUrl"
                :alt="product.name"
                class="card-img-top product-image"
            />
            <div v-else class="no-image">Brak zdjecia</div>

            <div class="card-body d-flex flex-column">
              <h5 class="card-title text-truncate" :title="product.name">{{ product.name }}</h5>
              <p class="mb-0 mt-auto"><strong>{{ formatPrice(product) }}</strong></p>
            </div>
          </router-link>
        </div>
      </div>
    </section>

    <!-- Kontakt + Mapa -->
    <section class="contact-section py-5">
      <div class="container">
        <div class="row g-4">
          <div class="col-md-5">
            <h3 class="section-title mb-3">Kontakt</h3>
            <p class="mb-2"><strong>cherry-kom</strong></p>
            <p class="mb-1">Al. Tysiąclecia Państwa Polskiego 7</p>
            <p class="mb-3">25-314 Kielce</p>
            <p class="mb-1"><strong>Telefon:</strong> +48 000 000 000</p>
            <p class="mb-1"><strong>E-mail:</strong> kontakt@cherry-kom.pl</p>
            <p class="mb-0"><strong>Godziny otwarcia:</strong><br />Pon-Pt: 10:00 - 16:00 <br /></p>
          </div>

          <div class="col-md-7">
            <h3 class="section-title mb-3">Znajdz nas</h3>
            <div class="map-wrapper shadow-sm">
              <iframe
                  src="https://www.google.com/maps?q=Al.+Tysi%C4%85clecia+Pa%C5%84stwa+Polskiego+7,+25-314+Kielce&output=embed"
                  width="100%"
                  height="360"
                  style="border:0;"
                  loading="lazy"
                  referrerpolicy="no-referrer-when-downgrade"
                  allowfullscreen
              ></iframe>
            </div>
          </div>
        </div>
      </div>
    </section>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const api = axios.create({ baseURL: 'http://localhost:8080/api', withCredentials: true })

const newest = ref([])
const loading = ref(false)
const error = ref('')

function formatPrice(p) {
  const price = p.priceInGrosze ?? p.price_in_grosze ?? null
  if (price == null) return 'Brak ceny'
  return (price / 100).toFixed(2) + ' zl'
}

async function loadNewest() {
  loading.value = true
  error.value = ''
  try {
    const { data } = await api.get('/products')
    newest.value = [...data].sort((a, b) => b.id - a.id).slice(0, 6)
  } catch (err) {
    console.error(err)
    error.value = 'Nie udalo sie pobrac produktow.'
  } finally {
    loading.value = false
  }
}

onMounted(loadNewest)
</script>

<style scoped>
.home-page {
  background: #f5f7fb;
  min-height: 100vh;
}

.section-title {
  font-weight: 600;
  color: #1a1a1a;
}

.product-card-link {
  display: block;
  text-decoration: none;
  color: inherit;
  transition: transform 0.15s, box-shadow 0.15s;
  border-radius: 12px;
  overflow: hidden;
}

.product-card-link:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12) !important;
  color: inherit;
}

.product-image {
  width: 100%;
  height: 220px;
  object-fit: cover;
}

.no-image {
  height: 220px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #e9ecef;
  color: #6c757d;
  font-weight: 600;
}

.banner-link {
  display: block;
  border-radius: 12px;
  overflow: hidden;
  transition: transform 0.15s, box-shadow 0.15s;
}

.banner-link:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.banner-img {
  width: 100%;
  height: auto;
  display: block;
}

.contact-section {
  background: #fff;
  border-top: 1px solid #e5e7eb;
}

.map-wrapper {
  border-radius: 12px;
  overflow: hidden;
}
</style>
