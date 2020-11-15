import Vue from 'vue'
import BrowseArt from '@/components/BrowseArt'
import VueRouter from 'vue-router'
import LandingPage from '@/components/LandingPage'
import ProfilePage from '@/components/ProfilePage'
import CreateArtwork from '@/components/CreateArtwork.vue'
import GalleryEvent from '@/components/GalleryEvent.vue'
import SpecificEvent from '@/components/SpecificEvent.vue'
import Checkout from '@/components/Checkout.vue'
import EditProfile from '@/components/EditProfile.vue'
import ViewArtwork from '@/components/ViewArtwork.vue'
import ArtworkUpload from '@/components/ArtworkUpload.vue'
import NotFound from '@/components/404.vue'

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
        path: '/user/edit/:username',
        name: 'EditProfile',
        component: EditProfile
    },
    {
        path: "/checkout/:username/:artworkId",
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
    },
    {
        path: "/artworks/:artworkId",
        name: "ViewArtwork",
        component: ViewArtwork
    },
    {
        path: "/upload",
        name: "ArtworkUpload",
        component: ArtworkUpload
    },
    {
        path: '/404',
        name: 'NotFound',
        component: NotFound

    }, {
        path: '*',
        redirect: '/404'
    }
]

export
const router = new VueRouter({
    routes
})


export default new VueRouter({
    routes: [
        /*{
          path: "/purchase",
          name: "PurchaseArtwork",
          component: PurchaseArtwork
        },
        {
          path: "/create-address",
          name: "CreateAddress",
          component: CreateAddress
        },*/
        // {
        //     path: "/create_artwork",
        //     name: "CreateArtwork",
        //     component: CreateArtwork
        // },
        // {
        //     path: "/artworks/:artworkid",
        //     name: "ViewArtwork",
        //     component: ViewArtwork
        // }

    ]
})

router.beforeEach((to, from, next) => {
    if (to.name !== 'LandingPage') {
        if (localStorage.getItem('username') === null) {
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