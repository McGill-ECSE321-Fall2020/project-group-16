<template>
  <div id="checkout">
    <div><h1>Checkout</h1></div>
    <hr />

    <div class="container">
      <div class="box-1" v-if="!orderId && !errorCheckout">
        <img class="art-img" :src="artwork.imageUrl" />
        <!-- <div class="artist">{{ artwork.artists[0] }}</div> -->
      </div>

      <div class="box-2" v-if="!orderId && !errorCheckout">
        <div class="title">{{ artwork.title }}</div>
        <p>Subtotal: ${{ artwork.price }}</p>
        <p>Taxes: ${{ tax }}</p>
        <div class="total">Total: ${{ total }}</div>
      </div>
    </div>

    <hr />

    <form
      @submit="placeOrder(username, artworkId)"
      v-if="!orderId && !errorCheckout"
      id="info"
    >
      <Shipment
        v-if="!shipmentId"
        v-bind:address="shippingAddress"
        v-on:add-shipment="getShipment($event)"
      />
      <Payment
        v-if="shipmentId && !paymentId"
        v-bind:payment="payment"
        v-on:add-payment="getPayment($event)"
      />

      <div v-if="shipmentId && paymentId">
        <h1>Review Order</h1>

        <br />
        <div class="row">
          <div class="col"><strong>Street Address:</strong></div>
          <div class="col">{{ shippingAddress.streetAddress }}</div>
        </div>
        <div class="row">
          <div class="col"><strong>Street Address 2:</strong></div>
          <div class="col">{{ shippingAddress.streetAddress2 }}</div>
        </div>

        <div class="row">
          <div class="col"><strong>Postal Code:</strong></div>
          <div class="col">{{ shippingAddress.postalCode }}</div>
        </div>
        <div class="row">
          <div class="col"><strong>City:</strong></div>
          <div class="col">{{ shippingAddress.city }}</div>
        </div>
        <div class="row">
          <div class="col"><strong>Province:</strong></div>
          <div class="col">{{ shippingAddress.province }}</div>
        </div>
        <div class="row">
          <div class="col"><strong>Country:</strong></div>
          <div class="col">{{ shippingAddress.country }}</div>
        </div>
        <br />
        <div class="row">
          <div class="col"><strong>Card Number:</strong></div>
          <div class="col">{{ payment.cardNumber }}</div>
        </div>
        <div class="row">
          <div class="col"><strong>Expires On:</strong></div>
          <div class="col">{{ payment.expirationDate }}</div>
        </div>
        <br />

        <input
          type="submit"
          value="Place Order"
          class="btn"
          v-bind:disabled="!username || !artworkId || !shipmentId || !paymentId"
        />
      </div>
    </form>

    <span v-if="errorCheckout" style="color: red"
      >Error: {{ errorCheckout }}
    </span>

    <div v-if="orderId">Order Placed</div>

    <hr />
  </div>
</template>

<script src="../js/checkout.js"></script>

<style scoped>
#checkout {
  padding: 30px;
}

#info {
  padding: 30px;
}

.container {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}

.box-1 {
  display: flex;
  flex-direction: column;
  flex: 1;
  align-items: center;
  justify-content: center;
  padding: 10px;
}

img {
  width: auto;
  height: 200px;
  padding: 30px;
  border: 1px solid #000000;
}

.box-2 {
  display: flex;
  flex-direction: column;
  flex: 1;
  padding: 10px;
}

.title {
  font-family: Montserrat;
  font-style: normal;
  font-weight: 600;
  font-size: 24px;
  line-height: 60px;
  color: #000000;
}

.total {
  font-family: Montserrat;
  font-style: normal;
  font-weight: normal;
  font-size: 18px;
  line-height: 24px;
  color: #000000;
}

button {
  width: 140px;
  height: 30px;

  border: 1px solid #000000;
  background-color: #e9e7db;
  color: #000000;
  box-sizing: border-box;
  border-radius: 3px;

  font-weight: normal;
  font-size: 14px;
  line-height: 17px;
  text-align: center;

  transition: 0.5s;
}

.btn {
  width: fit-content;
  background: black;
  text-align: center;
  cursor: pointer;
  transition: 0.5s;
  color: white;
}

.btn:hover {
  background-color: #006F45;
}

button:hover {
  background-color: #000000;
  color: #e9e7db;
}

.row {
  width: 40%;
}
</style>
