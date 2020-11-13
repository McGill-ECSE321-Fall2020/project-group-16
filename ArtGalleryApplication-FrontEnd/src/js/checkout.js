import { AXIOS } from "./axiosInstance";

import Shipment from "../components/Shipment.vue"
import Payment from "../components/Payment.vue"

export default {
  name: "Checkout",
  components: {
    Shipment,
    Payment
  },

  data() {
    return {
      orderId: '',
      username: '',
      artworkId: '',

      shipmentId: '',
      shippingAddress: {
        streetAddress: '',
        streetAddress2: '',
        postalCode: '',
        city: 'Montreal',
        province: 'QB',
        country: 'Canada'
      },

      paymentId: '',
      payment: {
        cardNumber: '',
        expirationDate: '',
        cvv: ''
      },

      errorCheckout: '',
      response: []
    }
  },

  created: function () {
    AXIOS.get(`/users/${this.$route.params.username}`)
      .then(
        this.username = this.$route.params.username
      )
      .catch((e) => {
        console.log(e);
        this.errorCheckout = e.response //"User Not Found"
        if (e.response) {
          console.log(e.response.data);
          console.log(e.response.status);
          console.log(e.response.headers);
        }
      });
    AXIOS.get(`/artworks/${this.$route.params.artworkId}`)
      .then(
        this.artworkId = this.$route.params.artworkId
      )
      .catch((e) => {
        console.log(e);
        this.errorCheckout = "Error Artwork Not Found"
      });
  },
  methods: {

    placeOrder: function (username, artworkId) {
      AXIOS.post("/orders/place-order/".concat(this.username), {}, {
        params: {
          artworkId: this.artworkId
        }
      })
        .then(response => {
          // JSON responses are automatically parsed]
          this.addPaymentAndShipment(response.data.orderId, this.paymentId, this.shipmentId)
          this.orderId = response.data.orderId
        })
        .catch(e => {
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.errorCheckout = errorMsg;
        });
    },

    addPaymentAndShipment(orderId, paymentId, shipmentId) {
      AXIOS.put(`/orders/${orderId}/add-payment-shipment`, {}, {
        params: {
          paymentId: paymentId,
          shipmentId: shipmentId
        }
      })
        .then(
          this.errorCheckout = ''
        )
        .catch(e => {
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.errorCheckout = errorMsg;
        });
    },

    getShipment(newShipment) {
      this.shipmentId = newShipment.shipmentId;
    },
    getPayment(newPayment) {
      this.paymentId = newPayment.paymentId;
    }
  }
};