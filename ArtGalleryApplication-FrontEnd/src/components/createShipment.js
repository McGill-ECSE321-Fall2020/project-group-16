import axios from "axios";
import CreateAddress from "./createAddress"

var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "CreateShipment",
  components: {
    CreateAddress
  },
  data() {
    return {
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

      errorShipment: '',
      response: []
    }
  },

  methods: {
    addAddress(newAddress, addressType) {
      if (addressType === 'return') {
        this.newReturnAddress = { ...newAddress }
      } else if (addressType === 'destination') {
        this.newDestAddress = { ...newAddress }
      }
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

      AXIOS.post("/shipments/", {}, {
        params: {
          toGallery: toGallery,
          estimatedArrivalTime: estimatedArrivalTime,
          estimatedArrivalDate: estimatedArrivalDate,
          returnAddressId: returnAddressId,
          destinationAddressId: destinationAddressId
        }
      }
      )
        .then(response => {
          // JSON responses are automatically parsed.
          this.$emit("add-shipment", response.data);
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