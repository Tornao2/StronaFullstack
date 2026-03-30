<template>
  <div class="search-wrapper" ref="wrapperRef">
    <div class="search-input-row">
      <input
          ref="inputRef"
          v-model="query"
          type="text"
          class="search-input"
          placeholder="Szukaj produktu..."
          autocomplete="off"
          @input="onInput"
          @keydown.down.prevent="moveDown"
          @keydown.up.prevent="moveUp"
          @keydown.enter.prevent="confirmSelection"
          @keydown.escape="close"
          @focus="onFocus"
      />
      <button class="search-btn" @click="submitSearch">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
        </svg>
      </button>
    </div>

    <!-- Dropdown z sugestiami -->
    <transition name="drop">
      <div v-if="showDropdown" class="dropdown">
        <div v-if="loading" class="dropdown-state">
          <span class="spinner" /> Szukam...
        </div>

        <div v-else-if="suggestions.length === 0 && query.length >= 2" class="dropdown-state">
          Brak wyników dla „{{ query }}"
        </div>

        <ul v-else>
          <li
              v-for="(item, i) in suggestions"
              :key="item.id"
              class="suggestion-item"
              :class="{ active: i === activeIndex }"
              @mousedown.prevent="selectItem(item)"
              @mousemove="activeIndex = i"
          >
            <img v-if="item.imageUrl" :src="item.imageUrl" class="suggestion-img" alt="" />
            <div v-else class="suggestion-img no-img" />
            <div class="suggestion-info">
              <span class="suggestion-name" v-html="highlight(item.name)" />
              <span class="suggestion-price">{{ formatPrice(item.priceInGrosze) }}</span>
            </div>
          </li>
        </ul>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()

const api = axios.create({ baseURL: 'http://localhost:8080/api', withCredentials: true })

const query = ref('')
const suggestions = ref([])
const loading = ref(false)
const showDropdown = ref(false)
const activeIndex = ref(-1)
const wrapperRef = ref(null)
const inputRef = ref(null)

let debounceTimer = null

function onInput() {
  activeIndex.value = -1
  clearTimeout(debounceTimer)

  if (query.value.trim().length < 2) {
    suggestions.value = []
    showDropdown.value = false
    return
  }

  loading.value = true
  showDropdown.value = true

  debounceTimer = setTimeout(async () => {
    try {
      const { data } = await api.get('/products/search', { params: { q: query.value.trim() } })
      suggestions.value = data.slice(0, 8) // max 8 sugestii
    } catch {
      suggestions.value = []
    } finally {
      loading.value = false
    }
  }, 280)
}

function onFocus() {
  if (query.value.trim().length >= 2 && suggestions.value.length > 0) {
    showDropdown.value = true
  }
}

function moveDown() {
  if (!showDropdown.value) return
  activeIndex.value = Math.min(activeIndex.value + 1, suggestions.value.length - 1)
}

function moveUp() {
  activeIndex.value = Math.max(activeIndex.value - 1, -1)
}

function confirmSelection() {
  if (activeIndex.value >= 0 && suggestions.value[activeIndex.value]) {
    selectItem(suggestions.value[activeIndex.value])
  } else {
    submitSearch()
  }
}

function selectItem(item) {
  close()
  router.push(`/product/${item.id}`)
}

function submitSearch() {
  const q = query.value.trim()
  if (!q) return
  close()
  router.push({ path: '/search', query: { q } })
}

function close() {
  showDropdown.value = false
  activeIndex.value = -1
}

// Zamknij dropdown po kliknięciu poza komponentem
function onClickOutside(e) {
  if (wrapperRef.value && !wrapperRef.value.contains(e.target)) {
    close()
  }
}

onMounted(() => document.addEventListener('mousedown', onClickOutside))
onUnmounted(() => document.removeEventListener('mousedown', onClickOutside))

function formatPrice(grosze) {
  if (grosze == null) return ''
  return (grosze / 100).toFixed(2) + ' zł'
}

// Podświetla wpisany fragment w nazwie produktu
function highlight(name) {
  if (!query.value.trim()) return name
  const escaped = query.value.trim().replace(/[.*+?^${}()|[\]\\]/g, '\\$&')
  return name.replace(new RegExp(`(${escaped})`, 'gi'), '<mark>$1</mark>')
}
</script>

<style scoped>
.search-wrapper {
  position: relative;
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 480px;
}

.search-input-row {
  display: flex;
  gap: 0;
}

.search-input {
  flex: 1;
  height: 38px;
  padding: 0 14px;
  border: 1px solid rgba(255,255,255,0.2);
  border-right: none;
  border-radius: 8px 0 0 8px;
  background: rgba(255,255,255,0.1);
  color: #fff;
  font-size: 14px;
  outline: none;
  transition: background .15s, border-color .15s;
}

.search-input::placeholder { color: rgba(255,255,255,0.5); }
.search-input:focus {
  background: rgba(255,255,255,0.18);
  border-color: rgba(255,255,255,0.4);
}

.search-btn {
  height: 38px;
  padding: 0 14px;
  background: #e3342f;
  border: none;
  border-radius: 0 8px 8px 0;
  color: #fff;
  cursor: pointer;
  display: flex;
  align-items: center;
  transition: background .15s;
}
.search-btn:hover { background: #cc2a26; }

/* Dropdown */
.dropdown {
  position: absolute;
  top: calc(100% + 4px);
  left: 0;
  right: 0;
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  box-shadow: 0 8px 24px rgba(0,0,0,0.12);
  z-index: 2000;
  overflow: hidden;
}

.dropdown ul {
  list-style: none;
  margin: 0;
  padding: 4px 0;
}

.suggestion-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 12px;
  cursor: pointer;
  transition: background .1s;
}
.suggestion-item.active,
.suggestion-item:hover { background: #f3f4f6; }

.suggestion-img {
  width: 40px;
  height: 40px;
  object-fit: cover;
  border-radius: 6px;
  flex-shrink: 0;
}

.no-img {
  background: #e9ecef;
}

.suggestion-info {
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.suggestion-name {
  font-size: 13px;
  font-weight: 500;
  color: #111;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.suggestion-name :deep(mark) {
  background: #fde68a;
  color: inherit;
  border-radius: 2px;
  padding: 0 1px;
}

.suggestion-price {
  font-size: 12px;
  color: #6b7280;
  margin-top: 1px;
}

.dropdown-state {
  padding: 14px 16px;
  font-size: 13px;
  color: #6b7280;
  display: flex;
  align-items: center;
  gap: 8px;
}

.spinner {
  width: 14px;
  height: 14px;
  border: 2px solid #e5e7eb;
  border-top-color: #e3342f;
  border-radius: 50%;
  animation: spin .6s linear infinite;
  flex-shrink: 0;
}

@keyframes spin { to { transform: rotate(360deg); } }

/* Animacja wysuwania */
.drop-enter-active { transition: opacity .15s, transform .15s; }
.drop-leave-active { transition: opacity .1s, transform .1s; }
.drop-enter-from  { opacity: 0; transform: translateY(-6px); }
.drop-leave-to    { opacity: 0; transform: translateY(-6px); }
</style>