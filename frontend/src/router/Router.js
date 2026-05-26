import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/Home.vue'
import Products from '../views/Products.vue'
import Product from '../views/Product.vue'
import PaymentSuccess from '../views/PaymentSuccess.vue'
import PaymentFailed from '../views/PaymentFailed.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Profile from '../views/Profile.vue'
import Orders from "../views/Orders.vue";

const routes = [
    { path: '/', redirect: '/home' },
    { path: '/home', component: Home },

    { path: '/login',    component: Login },
    { path: '/register', component: Register },
    { path: '/profile',  component: Profile },

    // Kategorie
    { path: '/laptops',      component: Products, props: { category: 'laptops' } },
    { path: '/cpus',         component: Products, props: { category: 'cpus' } },
    { path: '/gpus',         component: Products, props: { category: 'gpus' } },
    { path: '/ram',          component: Products, props: { category: 'ram' } },
    { path: '/motherboards', component: Products, props: { category: 'motherboards' } },
    { path: '/supplies',     component: Products, props: { category: 'supplies' } },
    { path: '/cooling',      component: Products, props: { category: 'cooling' } },
    { path: '/preassembled', component: Products, props: { category: 'preassembled' } },

    // Produkt i wyszukiwanie
    { path: '/product/:id', component: Product },
    { path: '/search',      component: Products },

    // Płatność — muszą pasować do URL-i w PaymentService.java
    { path: '/payment-success', component: PaymentSuccess },
    { path: '/payment-failed',  component: PaymentFailed },

    // Moje zamowienia
    { path: '/orders',       component: Orders },

]

const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) return savedPosition
        return { top: 0 }
    },
})

export default router