import Vue from 'vue'
import VueRouter from 'vue-router'
import Hello from '@/components/Hello'
import LandingPage from '@/components/LandingPage'
import ProfilePage from '@/components/ProfilePage'

Vue.use(VueRouter)

const routes = [{
        path: '/',
        name: 'Hello',
        component: Hello
    },
    {
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