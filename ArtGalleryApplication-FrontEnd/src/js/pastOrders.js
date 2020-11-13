import { AXIOS } from './axiosInstance'

export default {
    name: "PastOrders",
    props: ['username'],
    data() {
        return {
            orders: []
        }
    },
    created: function() {
        var self = this
        AXIOS.get(`/orders/get-by-username/${this.username}`)
            .then((response) => {
                console.log(response.data);
                this.orders = response.data
            })
            .catch(function(error) {
                console.log(error)
            })
    },
}