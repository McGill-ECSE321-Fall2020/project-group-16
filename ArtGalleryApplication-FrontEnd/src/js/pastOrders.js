import { AXIOS } from './axiosInstance'

export default {
    name: "PastOrders",
    props: ['username'],

    /**
     * declaration of the page's data
     */
    data() {
        return {
            orders: [],
        }
    },

    /**
     * get the orders of the specific user
     */
    mounted: function () {
        var self = this
        AXIOS.get(`/orders/get-by-user/${this.username}`)
            .then((response) => {
                this.orders = response.data
            })
            .catch(function (error) {
                console.log(error)
            })
    },
    methods: {
        /**
         * get the height of the picture
         */
        getHeight: function () {
            return { height: document.getElementById("profile-info").offsetHeight + "px" }
        },

        /**
         * on button press, go to the specific artwork
         * @param {*} id 
         */
        goToArtwork: function (id) {
            this.$router.push({ name: "ViewArtwork", params: { artworkId: id } })
        }
    }
}