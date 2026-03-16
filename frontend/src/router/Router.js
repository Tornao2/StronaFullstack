import {createRouter, createWebHistory} from "vue-router";

import Home from '../views/Home.vue'
import Products from "../views/Products.vue";

const routes = [
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '/home',
        component: Home
    },
    {
        path: '/laptops',
        component: Products,
        props: {
            category: 'laptops'
        }
    },
    {
        path: '/cpus',
        component: Products,
        props: {
            category: 'cpus'
        }
    },
    {
        path: '/gpus',
        component: Products,
        props: {
            category: 'gpus'
        }
    },
    {
        path: '/ram',
        component: Products,
        props: {
            category: 'ram'
        }
    },
    {
        path: '/motherboards',
        component: Products,
        props: {
            category: 'motherboards'
        }
    },
    {
        path: '/supplies',
        component: Products,
        props: {
            category: 'supplies'
        }
    },
    {
        path: '/cooling',
        component: Products,
        props: {
            category: 'cooling'
        }
    },
    {
        path: '/preassembled',
        component: Products,
        props: {
            category: 'preassembled'
        }
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router