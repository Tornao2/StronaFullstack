import { ref, computed } from 'vue'
import axios from 'axios'

const user = ref(null)
const loading = ref(false)
const checked = ref(false)

const api = axios.create({ baseURL: 'http://localhost:8080/api', withCredentials: true })

export function useAuth() {

    const isLoggedIn = computed(() => !!user.value)
    const isAdmin = computed(() => user.value?.role === 'ROLE_ADMIN')

    async function checkAuth() {
        if (checked.value) return
        loading.value = true
        try {
            const { data } = await api.get('/auth/status')
            if (data.authenticated) {
                const profile = await api.get('/users/me')
                user.value = profile.data
            }
        } catch {
            user.value = null
        } finally {
            loading.value = false
            checked.value = true
        }
    }

    async function logout() {
        try {
            await api.post('/api/logout')
        } catch {}
        user.value = null
        checked.value = false
    }

    function setUser(userData) {
        user.value = userData
        checked.value = true
    }

    function clearUser() {
        user.value = null
        checked.value = false
    }

    return { user, isLoggedIn, isAdmin, loading, checkAuth, logout, setUser, clearUser }
}