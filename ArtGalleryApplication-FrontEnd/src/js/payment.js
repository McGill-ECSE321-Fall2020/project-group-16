import { AXIOS } from "./axiosInstance";

export default {
  name: "Payment",
  props: ["payment"],

  /**
  * declaration of the page's data
  */
  data() {
    return {
      errorPayment: ''
    }
  },
  methods: {

    /**
     * create payment method
     */
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
      var expirationDate = "20" + this.payment.expirationDate.slice(-2) + "-" + this.payment.expirationDate.substring(0, 2) + "-" + "01";
      var cardNumber = this.payment.cardNumber.split(" ").join("");

      /**
       * post HTTP request to backend to create a payment with the inputted parameters
       */
      AXIOS.post("/payments/", {}, {
        params: {
          cardNumber: cardNumber,
          expirationDate: expirationDate,
          cvv: this.payment.cvv,
          paymentForm: "CreditCard",
          paymentDate: paymentDate,
          paymentTime: paymentTime
        }
      }
      )
        .then(response => {
          this.$emit("add-payment", response.data);
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