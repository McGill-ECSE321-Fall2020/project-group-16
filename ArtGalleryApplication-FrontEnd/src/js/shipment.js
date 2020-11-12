import axios from "axios";
import Address from "../components/Address.vue"

var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "Shipment",
  components: {
    Address
  },
  data() {
    return {
      toGallery: false,
      newDestAddress: {
        streetAddress: '',
        streetAddress2: '',
        postalCode: '',
        city: 'Montreal',
        province: 'QB',
        country: 'Canada'
      },

      errorShipment: '',
      response: []
    }
  },

  methods: {
    updateAddresses() {
      console.log(!this.toGallery)
      if (this.toGallery) {
        // reset address inputs 
        this.newDestAddress = {
          streetAddress: '',
          streetAddress2: '',
          postalCode: '',
          city: 'Montreal',
          province: 'QB',
          country: 'Canada'
        }
      } else {
        // sets addresses to Gallery address 
        this.newDestAddress = {
          streetAddress: 'GALLERY ADDRESS',
          streetAddress2: 'GALLERY ADDRESS 2',
          postalCode: 'GALLERY POSTAL CODE',
          city: 'Montreal',
          province: 'QB',
          country: 'Canada'
        }
      }
    },

    createShipment: function (toGallery, returnAddress, destinationAddress) {
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

      AXIOS.post("/shipments/full/", {}, {
        params: {
          toGallery: toGallery,
          estimatedArrivalTime: estimatedArrivalTime,
          estimatedArrivalDate: estimatedArrivalDate,

          destStreetAddress: destinationAddress.streetAddress,
          destStreetAddress2: destinationAddress.streetAddress2,
          destPostalCode: destinationAddress.postalCode,
          destCity: destinationAddress.city,
          destProvince: destinationAddress.province,
          destCountry: destinationAddress.country,
        }
      })
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
    },
  }
};