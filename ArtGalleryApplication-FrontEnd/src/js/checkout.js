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
      artwork: {
        title: "",
        description: "",
        dimensions: "",
        medium: "",
        collection: "",
        creationDate: "",
        price: "",
        imageUrl: "",
        status: ""
      },

      tax: '',
      total: '',

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
        this.errorCheckout = e.response //"User Not Found"
      });
    AXIOS.get(`/artworks/${this.$route.params.artworkId}`)
      .then(response => {
        this.artworkId = this.$route.params.artworkId

        const price = response.data.price
        const tax = price * 0.15
        const total = price * 1.15

        this.tax = tax.toFixed(2)
        this.total = total.toFixed(2)

        this.artwork.title = response.data.title
        this.artwork.price = price.toFixed(2)
        this.artwork.imageUrl = response.data.imageUrl
      })
      .catch((e) => {
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
          this.addPaymentAndShipment(response.data.orderId, this.paymentId, this.shipmentId, this.total)
          this.orderId = response.data.orderId
        })
        .catch(e => {
          var errorMsg = e.response.data.message;
          this.errorCheckout = errorMsg;
        });
    },

    addPaymentAndShipment(orderId, paymentId, shipmentId, total) {
      AXIOS.put(`/orders/${orderId}/add-payment-shipment`, {}, {
        params: {
          paymentId: paymentId,
          shipmentId: shipmentId,
          amount: total
        }
      })
        .then(
          this.errorCheckout = ''
        )
        .catch(e => {
          var errorMsg = e.response.data.message;
          this.errorCheckout = errorMsg;
        });
    },

    getShipment(newShipment) {
      this.shipmentId = newShipment.shipmentId;
    },

    getPayment(newPayment) {
      this.paymentId = newPayment.paymentId;
    },

    showShipping() {
      var show = document.getElementById("shipping");
      var text = document.getElementById("toggle-shipping");
 
      if(show.style.display == "none") {
        show.style.display = "block";
        text.textContent = " - SHIPPING";
      } else {
        show.style.display = "none";
        text.textContent = " + SHIPPING";
      }
    },

    showPayment() {
      var show = document.getElementById("payment");
      var text = document.getElementById("toggle-payment");
      
      if(show.style.display == "none") {
        show.style.display = "block";
        text.textContent = " - PAYMENT";
      } else {
        show.style.display = "none";
        text.textContent = " + PAYMENT";
      }
    }
  }
}