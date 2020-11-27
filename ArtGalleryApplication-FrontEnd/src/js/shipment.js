import { AXIOS } from "./axiosInstance";

import Address from "../components/Address.vue"

export default {
  name: "Shipment",
  components: {
    Address
  },
  props: ["address"],

    /**
     * declaration of the page's data
     */
  data() {
    return {
      toGallery: false,

      errorShipment: '',
      response: []
    }
  },

  methods: {

    /**
     * Update address object
     */
    updateAddresses() {
      if (this.toGallery) {
        // reset address inputs 
        this.address.streetAddress = ''
        this.address.streetAddress2 = ''
        this.address.postalCode = ''
        this.address.city = 'Montreal'
        this.address.province = 'QB'
        this.address.country = 'Canada'
      } else {
        // sets addresses to Gallery address 
        this.address.streetAddress = '845 Sherbrooke St W'
        this.address.streetAddress2 = ''
        this.address.postalCode = 'H3A 0G4'
        this.address.city = 'Montreal'
        this.address.province = 'QB'
        this.address.country = 'Canada'
      }
    },

    createShipment: function (toGallery, destinationAddress) {
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

      /**
       * Post HTTP request to the backend
       */
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