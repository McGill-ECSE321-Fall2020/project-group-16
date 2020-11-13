<template>
  <div id="payment">

    <h4>Payment</h4>
    <form @submit="createPayment">
      <div class="number">
        <label>Number</label>
        <input type="text" v-model="payment.cardNumber" placeholder="Card Number" v-cardformat:formatCardNumber>
      </div>
      <div class="expiration">
        <label>Expiry</label>
        <input type="text" v-model="payment.expirationDate" placeholder="MM/YY" maxlength="7" v-cardformat:formatCardExpiry >
      </div>
      <div class="cvc">
        <label>Card CVC</label>
        <input type="text" v-model="payment.cvv" placeholder="CVV" maxlength="4" v-cardformat:formatCardCVC>
      </div>
      <input
        v-bind:disabled="!payment.cardNumber || !payment.expirationDate || !payment.cvv"
        type="submit"
        value="Continue"
        class="btn"
      />
    </form>

    <span v-if="errorPayment" style="color: red"
      >Error: {{ errorPayment }}
    </span>

    <hr />

  </div>
</template>

<script src="../js/payment.js"></script>

<style>
#payment {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>
