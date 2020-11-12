import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import BrowseArt from '@/components/BrowseArt'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/browseart',
      name: 'BrowseArt',
      component: BrowseArt
    },
    {
      path: '/artPage',
      name: 'ArtPage',
      component: ArtPage
    }
  ]
})
