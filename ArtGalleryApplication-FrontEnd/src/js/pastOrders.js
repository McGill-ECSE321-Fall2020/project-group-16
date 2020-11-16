import { AXIOS } from './axiosInstance'

export default {
    name: "PastOrders",
    props: ['username'],
    data() {
        return {
            orders: [],
        }
    },
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
        getHeight: function () {
            return { height: document.getElementById("profile-info").offsetHeight + "px" }
        },
        goToArtwork: function (id) {
            this.$router.push({ name: "ViewArtwork", params: { artworkId: id } })
        }
    }
}