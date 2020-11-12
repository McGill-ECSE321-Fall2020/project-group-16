import Shipment from "../components/Shipment.vue"
import Payment from "../components/Payment.vue"

import axios from "axios";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "Checkout",
  components: {
    Shipment,
    Payment
  },

  data() {
    return {
      artworks: [],
      users: [],
      orders: [],
      shipments: [],
      payments: [],

      newOrder: '',
      selectedOrderId: '',
      shipmentId: '',
      paymentId: '',

      selectedUser: '',
      selectedArtwork: '',

      errorUsers: '',
      errorArtworks: '',
      errorOrders: '',
      errorAddress: '',
      errorCheckout: '',
      response: []
    }
  },

  created: function () {
    // Initializing persons from backend
    AXIOS.get("/users")
      .then(response => {
        // JSON responses are automatically parsed.
        this.users = response.data;
      })
      .catch(e => {
        this.errorUsers = e;
      });
    // Initializing events
    AXIOS.get("/artworks")
      .then(response => {
        this.artworks = response.data;
      })
      .catch(e => {
        this.errorArtworks = e;
        // this.errors.push(e)
      });
    AXIOS.get("/orders")
      .then(response => {
        this.orders = response.data;
      })
      .catch(e => {
        this.errorOrders = e;
        // this.errors.push(e)
      });
    // AXIOS.get("/address")
    //   .then(response => {
    //     this.addresses = response.data;
    //   })
    //   .catch(e => {
    //     this.errorAddress = e;
    //     // this.errors.push(e)
    //   });
    AXIOS.get("/shipments")
      .then(response => {
        this.shipments = response.data;
      })
      .catch(e => {
        this.errorAddress = e;
        // this.errors.push(e)
      });
    AXIOS.get("/payments")
      .then(response => {
        this.payments = response.data;
      })
      .catch(e => {
        this.errorAddress = e;
        // this.errors.push(e)
      });
  },
  methods: {

    placeOrder: function (username, artworkId) {
      var indexUser = this.users.map(x => x.username).indexOf(username)
      var indexArtwork = this.artworks.map(x => x.artworkId).indexOf(parseInt(artworkId))
      var user = this.users[indexUser]
      var artwork = this.artworks[indexArtwork]

      AXIOS.post("/orders/place-order/".concat(user.username), {}, {
        params: {
          artworkId: artwork.artworkId
        }
      })
        .then(response => {
          // JSON responses are automatically parsed]
          this.addPaymentAndShipment(response.data.orderId, this.paymentId, this.shipmentId)
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
        .then(response => {
          this.orders.push(response.data);
          this.errorCheckout = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.errorCheckout = errorMsg;
        });
    },

    getShipment(newShipment) {
      this.shipments.push(newShipment)
      this.shipmentId = newShipment.shipmentId;
      console.log(newShipment.shipmentId)
    },
    getPayment(newPayment) {
      this.payments.push(newPayment)
      this.paymentId = newPayment.paymentId;
      console.log(newPayment.paymentId)
    }
  }
};