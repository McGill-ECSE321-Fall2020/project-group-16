import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import PurchaseArtwork from '@/components/PurchaseArtwork.vue'
import CreateAddress from '@/components/CreateAddress.vue'
import CreateArtwork from '@/components/CreateArtwork.vue'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
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
})
