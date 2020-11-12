import Vue from 'vue'
import VueRouter from 'vue-router'
import LandingPage from '@/components/LandingPage'
import ProfilePage from '@/components/ProfilePage'
import PurchaseArtwork from '@/components/PurchaseArtwork.vue'
import CreateAddress from '@/components/CreateAddress.vue'
import CreateArtwork from '@/components/CreateArtwork.vue'

Vue.use(VueRouter)

const routes = [{
        path: '/landing',
        name: 'LandingPage',
        component: LandingPage
    },
    {
        path: '/user/:username',
        name: 'ProfilePage',
        component: ProfilePage,
        meta: {
            isAuthenticated: true
        }
    },

    {
        path: "/purchase",
        name: "PurchaseArtwork",
        component: PurchaseArtwork
    },
    {
        path: "/create-address",
        name: "CreateAddress",
        component: CreateAddress
    },
    {
        path: "/create_artwork",
        name: "CreateArtwork",
        component: CreateArtwork
    }
]

export
const router = new VueRouter({
    routes
})

router.beforeEach((to, from, next) => {
    if (to.name != 'LandingPage') {
        if (localStorage.getItem('username') == null) {
            next({
                name: LandingPage
            })
        } else {
            next()
        }
    } else {
        next({
            name: LandingPage
        })
    }
})