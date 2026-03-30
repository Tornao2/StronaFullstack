<template>
  <div class="auth-page">
    <div class="auth-card">
      <h2 class="auth-title">Zaloguj się</h2>

      <div v-if="error" class="alert alert-danger py-2">{{ error }}</div>

      <form @submit.prevent="login">
        <div class="mb-3">
          <label class="form-label">E-mail</label>
          <input
            v-model="form.email"
            type="email"
            class="form-control"
            placeholder="twoj@email.pl"
            required
            autocomplete="email"
          />
        </div>

        <div class="mb-4">
          <label class="form-label">Hasło</label>
          <input
            v-model="form.password"
            type="password"
            class="form-control"
            placeholder="••••••••"
            required
            autocomplete="current-password"
          />
        </div>

        <button class="btn btn-danger w-100 btn-lg" :disabled="loading">
          <span v-if="loading" class="spinner-border spinner-border-sm me-2" />
          {{ loading ? 'Logowanie...' : 'Zaloguj się' }}
        </button>
      </form>

      <hr class="my-4" />

      <a href="http://localhost:8080/oauth2/authorization/google" class="btn btn-outline-secondary w-100">
        Zaloguj przez Google
      </a>

      <p class="mt-4 text-center text-muted small">
        Nie masz konta?
        <router-link to="/register" class="text-danger">Zarejestruj się</router-link>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()

const form = ref({ email: '', password: '' })
const loading = ref(false)
const error = ref('')

async function login() {
  error.value = ''
  loading.value = true
  try {
    // Spring formLogin oczekuje application/x-www-form-urlencoded z polami username i password
    const params = new URLSearchParams()
    params.append('username', form.value.email)
    params.append('password', form.value.password)

    await axios.post('http://localhost:8080/api/login', params, {
      withCredentials: true,
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    })

    router.push('/home')
  } catch (err) {
    if (err.response?.status === 401) {
      error.value = 'Błędny e-mail lub hasło.'
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
  max-width: 420px;
  box-shadow: 0 4px 24px rgba(0,0,0,0.07);
}

.auth-title {
  font-size: 1.6rem;
  font-weight: 600;
  margin-bottom: 28px;
  text-align: center;
  color: #1a1a1a;
}
</style>