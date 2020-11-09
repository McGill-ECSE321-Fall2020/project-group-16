import axios from "axios";
import createAddress from "./createAddress";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "create-shipment",
  data() {
    return {
      addresses: [],
      shipments: [],

      newReturnAddress: {
        addressId: '',
        streetAddress: '',
        streetAddress2: '',
        postalCode: '',
        city: 'Montreal',
        province: 'QB',
        country: 'Canada'
      },
      newDestAddress: {
        addressId: '',
        streetAddress: '',
        streetAddress2: '',
        postalCode: '',
        city: 'Montreal',
        province: 'QB',
        country: 'Canada'
      },
      newShipment: {
        toGallery: false
      },

      errorAddress: '',
      errorReturnAddress: '',
      errorDestinationAddress: '',
      errorShipment: '',
      response: []
    }
  },
  created: function () {
    // Initializing persons from backend
    AXIOS.get("/address")
      .then(response => {
        // JSON responses are automatically parsed.
        this.addresses = response.data;
      })
      .catch(e => {
        this.errorAddress = e;
      });

    AXIOS.get("/shipments")
      .then(response => {
        // JSON responses are automatically parsed.
        this.shipments = response.data;
      })
      .catch(e => {
        this.errorShipment = e;
      });
  },
  methods: {
    createAddress: function (newAddress) {
      AXIOS.post("/address/", {}, {
        params: {
          streetAddress: newAddress.streetAddress,
          streetAddress2: newAddress.streetAddress2,
          postalCode: newAddress.postalCode,
          city: newAddress.city,
          province: newAddress.province,
          country: newAddress.country,
        }
      }
      )
        .then(response => {
          // JSON responses are automatically parsed.
          this.addresses.push(response.data);
          this.errorAddress = "";
          newAddress.addressId = response.data.addressId

          // newAddress.streetAddress = "";
          // newAddress.streetAddress2 = "";
          // newAddress.postalCode = "";
        })
        .catch(e => {
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.errorAddress = errorMsg;
        });
    },
    createShipment: function (toGallery, returnAddressId, destinationAddressId) {

      // Get estimated date of delivery (1 week)
      var today = new Date();
      var nextWeek = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 7),
        month = '' + (nextWeek.getMonth() + 1),
        day = '' + nextWeek.getDate(),
        year = nextWeek.getFullYear();

      if (month.length < 2)
        month = '0' + month;
      if (day.length < 2)
        day = '0' + day;

      var estimatedArrivalTime = "16:00:00"
      var estimatedArrivalDate = year + '-' + month + '-' + day;

      // Get addresses
      var indexRetAddress = this.addresses.map(x => x.addressId).indexOf(parseInt(returnAddressId))
      var indexDestAddress = this.addresses.map(x => x.addressId).indexOf(parseInt(destinationAddressId))
      var returnAddress = this.addresses[indexRetAddress]
      var destinationAddress = this.addresses[indexDestAddress]

      AXIOS.post("/shipments/", {}, {
        params: {
          toGallery: toGallery,
          estimatedArrivalTime: estimatedArrivalTime,
          estimatedArrivalDate: estimatedArrivalDate,
          returnAddressId: returnAddress.addressId,
          destinationAddressId: destinationAddress.addressId
        }
      }
      )
        .then(response => {
          // JSON responses are automatically parsed.
          this.shipments.push(response.data);
          this.errorShipment = "";
        })
        .catch(e => {
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.errorShipment = errorMsg;
        });

      this.newReturnAddress.addressId = '';
      this.newDestAddress.addressId = '';

    },
  }
};