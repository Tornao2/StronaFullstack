<script setup>
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter()
const products = ref([])
const loading = ref(true)

const props = defineProps({
  category: String
})

function formatPrice(price) {
  return (price / 100).toFixed(2) + " zł"
}

function openProduct(id) {
  router.push(`/product/${id}`)
}


// Ogólnie to loading.value powinien mówić, że coś backend mieli z bazą danych, ale nie wiem, czy będzie to działało.
// Jeżeli nie będzie działało, to do wywalenia :D
// Jeżeli będzie się to zamieniało, żeby faktycznie brało dane z bazy, to kategorie można wziąść z "props.category" (tylko tam string jest).
async function loadProducts() {
  loading.value = true
  products.value = [
    {id: 1, name: "RTX 4070 Super", price_in_grosze: 99999, image_url: "https://media1.tenor.com/m/3WT2GvS0cLUAAAAC/laughing-funny.gif"},
    {id: 2, name: "RTX 3070 Super", price_in_grosze: 79999, image_url: "https://media1.tenor.com/m/3WT2GvS0cLUAAAAC/laughing-funny.gif"},
    {id: 3, name: "RTX 2070 Super", price_in_grosze: 89999, image_url: "https://media1.tenor.com/m/3WT2GvS0cLUAAAAC/laughing-funny.gif"},
    {id: 4, name: "RTX 1070 Super", price_in_grosze: 42999, image_url: "https://media1.tenor.com/m/3WT2GvS0cLUAAAAC/laughing-funny.gif"},
    {id: 5, name: "RTX 0070 not Super", price_in_grosze: 39999, image_url: "https://media1.tenor.com/m/3WT2GvS0cLUAAAAC/laughing-funny.gif"},
    {id: 6, name: "RTX -1070 Awful :(", price_in_grosze: 19999, image_url: "https://media1.tenor.com/m/3WT2GvS0cLUAAAAC/laughing-funny.gif"},
    {id: 7, name: "RTX -2070 Awful :(", price_in_grosze: 9999, image_url: "https://media1.tenor.com/m/3WT2GvS0cLUAAAAC/laughing-funny.gif"},
    {id: 8, name: "RTX -3070 Awful :(", price_in_grosze: 5999, image_url: "https://media1.tenor.com/m/3WT2GvS0cLUAAAAC/laughing-funny.gif"},
    {id: 9, name: "RTX -4070 This cannot get any worse >:(", price_in_grosze: 1999, image_url: "https://media1.tenor.com/m/3WT2GvS0cLUAAAAC/laughing-funny.gif"},
    {id: 10, name: "RTX -10070 wtf", price_in_grosze: 100, image_url: "https://media1.tenor.com/m/3WT2GvS0cLUAAAAC/laughing-funny.gif"},
    {id: 11, name: "RTX -9999999 ...", price_in_grosze: 9, image_url: "https://media1.tenor.com/m/3WT2GvS0cLUAAAAC/laughing-funny.gif"},
    {id: 12, name: "RTX -99999999999999999999999", price_in_grosze: -1, image_url: "https://media1.tenor.com/m/3WT2GvS0cLUAAAAC/laughing-funny.gif"},
  ]
  loading.value = false
}

onMounted(loadProducts)
</script>

<template>
  <h1>{{ category }}</h1>

  <div class="product-list">
    <div v-if="loading">
      Ładowanie...
    </div>

    <button class="product-card" v-for="product in products" :key="product.id" @click="openProduct(product.id)">
      <img class="product-image" :src="product.image_url"/>
      <div>
        <h3>{{ product.name }} {{ formatPrice(product.price_in_grosze) }}</h3>
      </div>
    </button>
  </div>
</template>

<style scoped>
.product-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.product-card {
  display: flex;
  align-items: center;
  gap: 12px;

  width: 100%;
  padding: 10px;

  border: none;
  border-radius: 8px;

  cursor: pointer;
  text-align: left;
}

.product-card:hover {
  background: #e6e6e6;
}

.product-image {
  width: 120px;
  height: 120px;
  object-fit: fill;
  border-radius: 6px;
}
</style>