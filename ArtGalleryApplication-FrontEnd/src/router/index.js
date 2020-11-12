import Vue from 'vue'

import VueRouter from 'vue-router'
import LandingPage from '@/components/LandingPage'
import ProfilePage from '@/components/ProfilePage'
import PurchaseArtwork from '@/components/PurchaseArtwork.vue'
import CreateArtwork from '@/components/CreateArtwork.vue'
import GalleryEvent from '@/components/GalleryEvent.vue'
import SpecificEvent from '@/components/SpecificEvent.vue'
import Checkout from '@/components/Checkout.vue'

Vue.use(VueRouter)

const routes = [{
  path: '/',
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
  path: "/checkout",
  name: "Checkout",
  component: Checkout
},
{
  path: "/create_artwork",
  name: "CreateArtwork",
  component: CreateArtwork
},
{
  path: '/events',
  name: 'GalleryEvents',
  component: GalleryEvent
},
{
  path: '/events/:id',
  name: 'SpecificEvent',
  component: SpecificEvent
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