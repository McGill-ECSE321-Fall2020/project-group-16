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
  name: "CreateAddress",
  props: ['title', "inputDisabled", "address"],
  data() {
    return {
      streetAddress: '',
      streetAddress2: '',
      postalCode: '',
      city: 'Montreal',
      province: 'QB',
      country: 'Canada'
    }
  },
  methods: {

    createAddress(e) {
      e.preventDefault();

      const newAddress = {
        addressId: '',
        streetAddress: this.streetAddress,
        streetAddress2: this.streetAddress2,
        postalCode: this.postalCode,
        city: this.city,
        province: this.province,
        country: this.country
      };

      AXIOS.post("/address/", {}, {
        params: {
          streetAddress: this.streetAddress,
          streetAddress2: this.streetAddress2,
          postalCode: this.postalCode,
          city: this.city,
          province: this.province,
          country: this.country,
        }
      }
      )
        .then(response => {
          // JSON responses are automatically parsed.
          this.$emit("add-address", response.data);
        })
        .catch(e => {
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
        });


    }
  }
};