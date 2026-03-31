<template>
  <div class="profile-page">
    <div class="container py-5">

      <div v-if="loading" class="alert alert-secondary">Ladowanie profilu...</div>
      <div v-else-if="error" class="alert alert-danger">{{ error }}</div>

      <div v-else-if="user" class="row g-4">

        <!-- Lewa kolumna — info -->
        <div class="col-md-4">
          <div class="profile-card text-center">
            <div class="avatar">{{ initials }}</div>
            <h4 class="mt-3 mb-1">{{ user.fullName }}</h4>
            <p class="text-muted small">{{ user.email }}</p>
            <span class="badge" :class="user.role === 'ROLE_ADMIN' ? 'bg-danger' : 'bg-secondary'">
              {{ user.role === 'ROLE_ADMIN' ? 'Administrator' : 'Klient' }}
            </span>

            <hr />

            <router-link to="/orders" class="btn btn-outline-primary w-100 mb-2">
              Moje zamowienia
            </router-link>

            <button class="btn btn-outline-danger w-100" @click="showDeleteConfirm = true">
              Usun konto
            </button>
          </div>
        </div>

        <!-- Prawa kolumna — edycja -->
        <div class="col-md-8">
          <div class="profile-card">
            <h5 class="mb-4">Edytuj dane</h5>

            <div v-if="saveSuccess" class="alert alert-success py-2">Dane zostaly zapisane!</div>
            <div v-if="saveError" class="alert alert-danger py-2">{{ saveError }}</div>

            <form @submit.prevent="saveProfile">

              <p class="section-label">Dane osobowe</p>

              <div class="mb-3">
                <label class="form-label">Imie i nazwisko</label>
                <input v-model="form.fullName" type="text" class="form-control" />
              </div>

              <div class="mb-4">
                <label class="form-label">Telefon</label>
                <input v-model="form.phoneNumber" type="tel" class="form-control" placeholder="+48 123 456 789" />
              </div>

              <p class="section-label">Adres do wysylki</p>

              <div class="mb-3">
                <label class="form-label">Adres</label>
                <input v-model="form.address" type="text" class="form-control" placeholder="ul. Przykladowa 1/2" />
              </div>

              <div class="row mb-4">
                <div class="col-5">
                  <label class="form-label">Kod pocztowy</label>
                  <input v-model="form.zipCode" type="text" class="form-control" placeholder="00-000" />
                </div>
                <div class="col-7">
                  <label class="form-label">Miasto</label>
                  <input v-model="form.city" type="text" class="form-control" placeholder="Warszawa" />
                </div>
              </div>

              <button class="btn btn-success btn-lg w-100" :disabled="saving">
                <span v-if="saving" class="spinner-border spinner-border-sm me-2" />
                {{ saving ? 'Zapisywanie...' : 'Zapisz zmiany' }}
              </button>
            </form>
          </div>
        </div>
      </div>

      <!-- Modal potwierdzenia usunięcia konta -->
      <div v-if="showDeleteConfirm" class="modal-overlay" @click.self="showDeleteConfirm = false">
        <div class="modal-box">
          <h5>Usunac konto?</h5>
          <p class="text-muted">Ta operacja jest nieodwracalna. Wszystkie Twoje dane zostana usuniete.</p>
          <div class="d-flex gap-2 justify-content-end mt-4">
            <button class="btn btn-outline-secondary" @click="showDeleteConfirm = false">Anuluj</button>
            <button class="btn btn-danger" :disabled="deleting" @click="deleteAccount">
              <span v-if="deleting" class="spinner-border spinner-border-sm me-2" />
              {{ deleting ? 'Usuwanie...' : 'Tak, usun konto' }}
            </button>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const api = axios.create({ baseURL: 'http://localhost:8080/api', withCredentials: true })

const user = ref(null)
const loading = ref(false)
const error = ref('')
const saving = ref(false)
const saveSuccess = ref(false)
const saveError = ref('')
const deleting = ref(false)
const showDeleteConfirm = ref(false)

const form = ref({
  fullName: '',
  phoneNumber: '',
  address: '',
  city: '',
  zipCode: '',
})

const initials = computed(() => {
  if (!user.value?.fullName) return '?'
  return user.value.fullName.split(' ').map(n => n[0]).join('').toUpperCase().slice(0, 2)
})

async function loadProfile() {
  loading.value = true
  error.value = ''
  try {
    const { data } = await api.get('/users/me')
    user.value = data
    form.value = {
      fullName: data.fullName ?? '',
      phoneNumber: data.phoneNumber ?? '',
      address: data.address ?? '',
      city: data.city ?? '',
      zipCode: data.zipCode ?? '',
    }
  } catch (err) {
    if (err.response?.status === 401) {
      router.push('/login')
    } else {
      error.value = 'Nie udalo sie zaladowac profilu.'
    }
  } finally {
    loading.value = false
  }
}

async function saveProfile() {
  saving.value = true
  saveSuccess.value = false
  saveError.value = ''
  try {
    const { data } = await api.put('/users/me', form.value)
    user.value = data
    saveSuccess.value = true
    setTimeout(() => { saveSuccess.value = false }, 3000)
  } catch {
    saveError.value = 'Nie udalo sie zapisac zmian.'
  } finally {
    saving.value = false
  }
}

async function deleteAccount() {
  deleting.value = true
  try {
    await api.delete('/users/me')
    router.push('/home')
  } catch {
    showDeleteConfirm.value = false
    saveError.value = 'Nie udalo sie usunac konta.'
  } finally {
    deleting.value = false
  }
}

onMounted(loadProfile)
</script>

<style scoped>
.profile-page {
  min-height: 100vh;
  background: #f5f7fb;
}

.profile-card {
  background: #fff;
  border-radius: 16px;
  padding: 32px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.06);
}

.avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background: #e3342f;
  color: #fff;
  font-size: 1.8rem;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
}

.section-label {
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.06em;
  color: #9ca3af;
  margin-bottom: 12px;
}

.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,0.45);
  z-index: 1050;
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-box {
  background: #fff;
  border-radius: 16px;
  padding: 32px;
  max-width: 420px;
  width: 100%;
  margin: 20px;
}
</style>