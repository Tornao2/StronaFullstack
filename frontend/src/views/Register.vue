<template>
  <div class="auth-page">
    <div class="auth-card">
      <h2 class="auth-title">Rejestracja</h2>

      <div v-if="error" class="alert alert-danger py-2">{{ error }}</div>
      <div v-if="success" class="alert alert-success py-2">
        Konto utworzone! <router-link to="/login">Zaloguj się</router-link>
      </div>

      <form v-if="!success" @submit.prevent="register">

        <p class="section-label">Dane konta</p>

        <div class="mb-3">
          <label class="form-label">E-mail *</label>
          <input v-model="form.email" type="email" class="form-control"
                 placeholder="twoj@email.pl" required autocomplete="email" />
          <div v-if="errors.email" class="form-text text-danger">{{ errors.email }}</div>
        </div>

        <div class="mb-3">
          <label class="form-label">Imię i nazwisko *</label>
          <input v-model="form.fullName" type="text" class="form-control"
                 placeholder="Jan Kowalski" required />
          <div v-if="errors.fullName" class="form-text text-danger">{{ errors.fullName }}</div>
        </div>

        <div class="mb-4">
          <label class="form-label">Hasło * <span class="text-muted small">(min. 6 znaków)</span></label>
          <input v-model="form.password" type="password" class="form-control"
                 placeholder="••••••••" required autocomplete="new-password" />
          <div v-if="errors.password" class="form-text text-danger">{{ errors.password }}</div>
        </div>

        <p class="section-label">Dane do wysyłki <span class="text-muted small">(opcjonalne)</span></p>

        <div class="mb-3">
          <label class="form-label">Telefon</label>
          <input v-model="form.phoneNumber" type="tel" class="form-control" placeholder="+48 123 456 789" />
        </div>

        <div class="mb-3">
          <label class="form-label">Adres</label>
          <input v-model="form.address" type="text" class="form-control" placeholder="ul. Przykładowa 1/2" />
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

        <button class="btn btn-danger w-100 btn-lg" :disabled="loading">
          <span v-if="loading" class="spinner-border spinner-border-sm me-2" />
          {{ loading ? 'Rejestrowanie...' : 'Zarejestruj się' }}
        </button>
      </form>

      <p class="mt-4 text-center text-muted small">
        Masz już konto?
        <router-link to="/login" class="text-danger">Zaloguj się</router-link>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const api = axios.create({ baseURL: 'http://localhost:8080/api', withCredentials: true })

const form = ref({
  email: '',
  password: '',
  fullName: '',
  phoneNumber: '',
  address: '',
  city: '',
  zipCode: '',
})

const loading = ref(false)
const error = ref('')
const success = ref(false)
const errors = ref({})

async function register() {
  error.value = ''
  errors.value = {}
  loading.value = true
  try {
    await api.post('/auth/register', form.value)
    success.value = true
  } catch (err) {
    if (err.response?.status === 409) {
      error.value = 'Ten e-mail jest już zajęty.'
    } else if (err.response?.status === 400) {
      // Błędy walidacji z GlobalExceptionHandler
      const data = err.response.data
      if (data.errors) {
        errors.value = data.errors
      } else {
        error.value = data.error ?? 'Błąd walidacji.'
      }
    } else {
      error.value = 'Wystąpił błąd. Spróbuj ponownie.'
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.auth-page {
  min-height: 80vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fb;
  padding: 40px 20px;
}

.auth-card {
  background: #fff;
  border-radius: 16px;
  padding: 48px 40px;
  width: 100%;
  max-width: 480px;
  box-shadow: 0 4px 24px rgba(0,0,0,0.07);
}

.auth-title {
  font-size: 1.6rem;
  font-weight: 600;
  margin-bottom: 28px;
  text-align: center;
  color: #1a1a1a;
}

.section-label {
  font-size: 0.8rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.06em;
  color: #9ca3af;
  margin-bottom: 12px;
}
</style>