<template>
  <div id="payment">
    <h4>Payment</h4>
    <form @submit="createPayment">
      <input type="text" v-model="cardNumber" placeholder="Card Number" />
      <input type="text" v-model="expirationDate" placeholder="MM/YY" />
      <input type="text" v-model="cvv" placeholder="CVV" />

      <input
        v-bind:disabled="!cardNumber || !expirationDate || !cvv"
        type="submit"
        value="Submit Payment"
        class="btn"
      />
    </form>

    <span v-if="errorPayment" style="color: red"
      >Error: {{ errorPayment }}
    </span>
  </div>
</template>

<script src="./payment.js"></script>

<style>
#payment {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>
