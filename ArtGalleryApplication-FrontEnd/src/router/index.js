import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import GalleryEvent from '@/components/GalleryEvent.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/events',
      name: 'GalleryEvents',
      component: GalleryEvent
    }
  ]
})
