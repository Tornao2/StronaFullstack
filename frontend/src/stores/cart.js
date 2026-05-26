import { ref, computed, watch } from 'vue'

const STORAGE_KEY = 'cart-items'

function loadInitial() {
    try {
        const raw = localStorage.getItem(STORAGE_KEY)
        return raw ? JSON.parse(raw) : []
    } catch {
        return []
    }
}

const items = ref(loadInitial())

watch(items, (val) => {
    try {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(val))
    } catch {}
}, { deep: true })

export function useCart() {

    function addToCart(product) {
        const existing = items.value.find(i => i.id === product.id)
        if (existing) {
            existing.quantity++
        } else {
            items.value.push({
                id: product.id,
                name: product.name,
                priceInGrosze: product.priceInGrosze ?? product.price_in_grosze ?? 0,
                imageUrl: product.imageUrl ?? product.image_url ?? '',
                quantity: 1,
            })
        }
    }

    function removeFromCart(productId) {
        items.value = items.value.filter(i => i.id !== productId)
    }

    function changeQuantity(productId, delta) {
        const item = items.value.find(i => i.id === productId)
        if (!item) return
        item.quantity += delta
        if (item.quantity <= 0) removeFromCart(productId)
    }

    function clearCart() {
        items.value = []
    }

    const totalCount = computed(() =>
        items.value.reduce((sum, i) => sum + i.quantity, 0)
    )

    const totalPrice = computed(() =>
        items.value.reduce((sum, i) => sum + i.priceInGrosze * i.quantity, 0)
    )

    function toOrderPayload() {
        return items.value.flatMap(i => Array(i.quantity).fill(i.id))
    }

    return { items, addToCart, removeFromCart, changeQuantity, clearCart, totalCount, totalPrice, toOrderPayload }
}