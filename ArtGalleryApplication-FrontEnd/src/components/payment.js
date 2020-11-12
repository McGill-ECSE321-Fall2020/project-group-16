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
  name: "Payment",
  data() {
    return {
      cardNumber: '',
      expirationDate: '',
      cvv: '',

      errorPayment: ''
    }
  },
  methods: {
    createPayment: function () {
      var today = new Date(),
        month = '' + (today.getMonth() + 1),
        day = '' + today.getDate(),
        year = today.getFullYear();

      if (month.length < 2)
        month = '0' + month;
      if (day.length < 2)
        day = '0' + day;

      var paymentDate = year + '-' + month + '-' + day;
      var paymentTime = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
      var expirationDate = "20" + this.expirationDate.slice(-2) + "-" + this.expirationDate.substring(0, 2) + "-" + "01";


      AXIOS.post("/payments/", {}, {
        params: {
          cardNumber: this.cardNumber,
          expirationDate: expirationDate,
          cvv: this.cvv,
          paymentForm: "CreditCard",
          paymentDate: paymentDate,
          paymentTime: paymentTime
        }
      }
      )
        .then(response => {
          // JSON responses are automatically parsed.
          this.$emit("add-payment", response.data);
          this.cardNumber = ''
          this.cvv = ''
          this.expirationDate = ''
          this.errorPayment = "";
        })
        .catch(e => {
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.errorPayment = errorMsg;
        });
    }
  }
};