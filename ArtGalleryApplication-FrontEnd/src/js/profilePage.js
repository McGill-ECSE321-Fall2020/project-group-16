import { AXIOS } from "./axiosInstance";
import UserProfileInfo from "../components/UserProfileInfo";
import PastOrders from "../components/PastOrders"
import ArtProduct from "../components/ArtProduct.vue"

export default {
    name: "ProfilePage",

    /**
     * declaration of the page's data
     */
    data() {
        return {
            username: "",
            user: "",
            currentUser: false,

            userArtworks: []
        };
    },
    components: {
        UserProfileInfo,
        PastOrders,
        ArtProduct
    },

    methods: {

        /**
         * Get the target user of the profile navigated to
         * @param {*} route 
         * @param {*} obj 
         */
        getUser(route, obj) {
            var self = this
            obj.username = route.params.username;
            AXIOS.get(`/users/${obj.username}`)
                .then((response) => {
                    obj.user = response.data;
                })
                .catch(function (e) {
                    if (e.response.data.message === "The user cannot be null") {
                        self.$router.push('/404')
                    }

                    console.log(e);
                });

            //get the target user's artworks
            AXIOS.get('/artworks/byArtist/?artist=' + obj.username
            ).then((response) => {
                this.userArtworks = response.data;
            }).catch(function (e) {
                console.log(e);
            });
        },
        setData(user, currentUser) {
            this.user = user,
                this.currentUser = currentUser
        }
    },

    /**
     * 
     * @param {*} to 
     * @param {*} from 
     * @param {*} next 
     */
    beforeRouteEnter(to, from, next) {
        next(vm => {
            if (localStorage.getItem("username") === to.params.username) {
                vm.currentUser = true
                vm.getUser(to, vm)
                next()
            } else if (localStorage.getItem("username" === null)) {
                next('/')
            } else {
                vm.currentUser = false
                vm.getUser(to, vm)
                next()
            }
        })


    },

    /**
     * 
     * @param {*} to 
     * @param {*} from 
     * @param {*} next 
     */
    beforeRouteUpdate(to, from, next) {
        this.user = null
        this.currentUser = false
        if (localStorage.getItem("username") === to.params.username) {
            this.currentUser = true
            this.getUser(to, this)
            next()
        } else if (localStorage.getItem("username" === null)) {

            next('/')
        } else {
            this.currentUser = false
            this.getUser(to, this)
            next()
        }

    }

};