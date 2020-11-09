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
  name: "create-payment",
  data() {
    return {
      payments: [],

      newPayment: {
        cardNumber: '',
        expirationDate: '',
        cvv: '',
        paymentForm: '',
        paymentDate: '',
        paymentTime: ''
      },

      errorPayment: '',
      response: []
    }
  },
  created: function () {
    // Initializing persons from backend
    AXIOS.get("/payments")
      .then(response => {
        // JSON responses are automatically parsed.
        this.payments = response.data;
      })
      .catch(e => {
        this.errorPayment = e;
      });
  },
  methods: {

    createPayment: function (cardNumber, cvv, expirationDateShort) {
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
      var expirationDate = "20" + expirationDateShort.slice(-2) + "-" + expirationDateShort.substring(0, 2) + "-" + "01";


      AXIOS.post("/payments/", {}, {
        params: {
          cardNumber: cardNumber,
          expirationDate: expirationDate,
          cvv: cvv,
          paymentForm: "CreditCard",
          paymentDate: paymentDate,
          paymentTime: paymentTime
        }
      }
      )
        .then(response => {
          // JSON responses are automatically parsed.
          this.payments.push(response.data);
          this.errorAddress = "";
        })
        .catch(e => {
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.errorPayment = errorMsg;
        });
    }
  }
};