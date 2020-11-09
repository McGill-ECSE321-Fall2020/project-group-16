import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import PlaceOrder from '@/components/PlaceOrder.vue'
import CreateAddress from '@/components/CreateAddress.vue'
import CreatePayment from '@/components/CreatePayment.vue'
import Checkout from '@/components/Checkout.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: "/place-order",
      name: "PlaceOrder",
      component: PlaceOrder
    },
    {
      path: "/create-address",
      name: "CreateAddress",
      component: CreateAddress
    },
    {
      path: "/create-payment",
      name: "CreatePayment",
      component: CreatePayment
    },
    {
      path: "/checkout",
      name: "Checkout",
      component: Checkout
    }
  ]
})
