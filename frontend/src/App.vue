<script setup>
import { ref, onMounted } from 'vue'
import { BApp, BNavbar, BNavbarNav, BNavItem } from 'bootstrap-vue-next'
import CartSidebar from './components/CartSidebar.vue'
import SearchBar from './components/SearchBar.vue'
import { useCart } from './stores/cart.js'
import { useRouter } from 'vue-router'
import { useAuth } from './composables/useAuth.js'

const router = useRouter()
const { user, isLoggedIn, checkAuth, logout } = useAuth()

onMounted(() => checkAuth())

async function handleLogout() {
  await logout()
  router.push('/home')
}

const cartOpen = ref(false)
const { totalCount } = useCart()
</script>

<template>
  <BApp>
    <BNavbar fixed="top" variant="dark" class="w-100 bg-dark" container="fluid">
      <div class="navbar-inner w-100">

        <div class="navbar-top-row">
          <!-- Logo -->
          <router-link to="/home" class="brand">
            <img src="/logo_nazwa.png" alt="cherry-kom" class="navbar-logo" />
          </router-link>

          <!-- Wyszukiwarka z autocomplete -->
          <SearchBar />

          <!-- Koszyk -->
          <button class="cart-btn" @click="cartOpen = true">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="9" cy="21" r="1"/><circle cx="20" cy="21" r="1"/>
              <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
            </svg>
            <span v-if="totalCount > 0" class="cart-badge">{{ totalCount }}</span>
          </button>

          <!-- Auth -->
          <div class="nav-auth">
            <template v-if="!isLoggedIn">
              <router-link to="/login" class="btn btn-outline-light btn-sm">Zaloguj się</router-link>
              <router-link to="/register" class="btn btn-danger btn-sm">Rejestracja</router-link>
            </template>
            <template v-else>
              <router-link to="/profile" class="btn btn-outline-light btn-sm">
                {{ user?.fullName?.split(' ')[0] ?? 'Profil' }}
              </router-link>
              <button class="btn btn-outline-danger btn-sm" @click="handleLogout">Wyloguj</button>
            </template>
          </div>
        </div>

        <!-- Nawigacja kategoriami -->
        <BNavbarNav class="nav-links">
          <BNavItem to="/home">Strona główna</BNavItem>
          <BNavItem to="/laptops">Laptopy</BNavItem>
          <BNavItem to="/cpus">Procesory</BNavItem>
          <BNavItem to="/gpus">Karty graficzne</BNavItem>
          <BNavItem to="/ram">RAM</BNavItem>
          <BNavItem to="/motherboards">Płyty główne</BNavItem>
          <BNavItem to="/supplies">Zasilacze</BNavItem>
          <BNavItem to="/cooling">Chłodzenia</BNavItem>
          <BNavItem to="/preassembled">Gotowe stacje</BNavItem>
        </BNavbarNav>

      </div>
    </BNavbar>

    <main class="page-content">
      <router-view />
    </main>

    <CartSidebar :is-open="cartOpen" @close="cartOpen = false" />
  </BApp>
</template>

<style>
.navbar-inner {
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 10px;
  padding: 10px 0;
}

.navbar-top-row {
  display: flex;
  align-items: center;
  gap: 12px;
  width: 100%;
}

.brand { display: flex; align-items: center; flex-shrink: 0; }
.navbar-logo { height: 44px; width: auto; }

.nav-links {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 4px;
}

.page-content { padding-top: 115px; }

.cart-btn {
  position: relative;
  background: transparent;
  border: 1px solid rgba(255,255,255,0.25);
  border-radius: 8px;
  color: #fff;
  padding: 7px 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
  flex-shrink: 0;
  transition: background .15s;
}
.cart-btn:hover { background: rgba(255,255,255,0.1); }

.cart-badge {
  position: absolute;
  top: -6px;
  right: -8px;
  background: #e3342f;
  color: #fff;
  border-radius: 50%;
  font-size: 0.7rem;
  font-weight: 700;
  width: 18px;
  height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.nav-auth {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-left: auto;
}
</style>