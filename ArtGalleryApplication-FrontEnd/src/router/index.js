import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import LandingPage from '@/components/LandingPage'

Vue.use(Router)

export default new Router({
    routes: [{
            path: '/',
            name: 'Hello',
            component: Hello
        },
        {
            path: '/landing',
            name: 'LandingPage',
            component: LandingPage
        }
    ]
})