import { AXIOS } from './axiosInstance'

export default {
    name: "PastOrders",
    props: ['username'],
    data() {
        return {
            orders: [],
        }
    },
    mounted: function() {
        var self = this
        AXIOS.get(`/orders/get-by-user/${this.username}`)
            .then((response) => {
                console.log(response.data);
                this.orders = response.data
            })
            .catch(function(error) {
                console.log(error)
            })
    },
    computed: {
        getHeight: function() {
            console.log(document.getElementById("profile-info").offsetHeight)
            return { height: document.getElementById("profile-info").offsetHeight + "px" }
        }
    }
}