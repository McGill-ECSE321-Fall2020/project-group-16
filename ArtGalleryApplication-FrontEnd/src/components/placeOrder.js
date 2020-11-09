import CreateAddress from "./createAddress"

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
  name: "PlaceOrder",
  components: {
    CreateAddress
  },

  data() {
    return {
      artworks: [],
      addresses: [],
      users: [],
      orders: [],

      newOrder: '',

      selectedUser: '',
      selectedArtwork: '',

      errorUsers: '',
      errorArtworks: '',
      errorOrders: '',
      errorAddress: '',
      errorPlaceOrder: '',
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
    AXIOS.get("/address")
      .then(response => {
        this.addresses = response.data;
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
          // JSON responses are automatically parsed.
          this.orders.push(response.data);
          this.errorPlaceOrder = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.errorPlaceOrder = errorMsg;
        });
    },

    addAddress(newAddress) {
      this.addresses.push(newAddress);

      // AXIOS.post("/address/", {}, {
      //   params: {
      //     streetAddress: streetAddress,
      //     streetAddress2: streetAddress2,
      //     postalCode: postalCode,
      //     city: city,
      //     province: province,
      //     country: country,
      //   }
      // }
      // )
      //   .then(response => {
      //     // JSON responses are automatically parsed.
      //     this.addresses.push(response.data);
      //     this.errorAddress = "";
      //   })
      //   .catch(e => {
      //     var errorMsg = e.response.data.message;
      //     console.log(errorMsg);
      //     this.errorAddress = errorMsg;
      //   });
    }
  }
};