<template>
  <div id="checkout">
    <div><h1>Checkout</h1></div>
    <hr>

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

    <hr>

    <form @submit="placeOrder(username, artworkId)" v-if="!orderId && !errorCheckout" id="info">
      <Shipment v-if="!shipmentId"  v-bind:address="shippingAddress" v-on:add-shipment="getShipment($event)" />
      <Payment v-if="shipmentId && !paymentId" v-bind:payment="payment" v-on:add-payment="getPayment($event)" />

      <div v-if="shipmentId && paymentId">
        <h4>Review Order</h4>
        
        <br>

        <table>
          <tr>
            <th>Street Address:</th>
            <td>{{ shippingAddress.streetAddress }}</td>
          </tr>
          <tr>
            <th>Street Address 2:</th>
            <td>{{ shippingAddress.streetAddress2 }}</td>
          </tr>
          <tr>
            <th>Postal Code:</th>
            <td>{{ shippingAddress.postalCode }}</td>
          </tr>
          <tr>
            <th>City:</th>
            <td>{{ shippingAddress.city }}</td>
          </tr>
           <tr>
            <th>Province:</th>
            <td>{{ shippingAddress.province }}</td>
          </tr>
           <tr>
            <th>Country:</th>
            <td>{{ shippingAddress.country }}</td>
          </tr>
        </table>

        <br>

       <table>
          <tr>
            <th>Card Number:</th>
            <td>{{ payment.cardNumber }}</td>
          </tr>
          <tr>
            <th>Expires On:</th>
            <td>{{ payment.expirationDate }}</td>
          </tr>
        </table>

        <br>

        <input
          type="submit"
          value="Place Order"
          class="btn"
          v-bind:disabled="
            !username || !artworkId || !shipmentId || !paymentId
          "
        />
      </div>
    </form>

    <span v-if="errorCheckout" style="color: red"
      >Error: {{ errorCheckout }}
      </span>

    <div v-if="orderId">
      Order Placed
    </div>
    
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
/* 
.btn {
  width: 140px;
  height: 40px;

  border: 1px solid #000000;
  background-color: #e9e7db;
  color: #000000;
  box-sizing: border-box;
  border-radius: 3px;

  font-weight: normal;
  font-size: 20px;
  line-height: 17px;
  text-align: center;

  transition: 0.5s;
} */

.btn {
    width: fit-content;
    background-color: black;
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


</style>
