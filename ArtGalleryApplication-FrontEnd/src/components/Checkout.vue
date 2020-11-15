<template>
  <div id="checkout">
    <!-- <hr />
    <h2>Objects</h2>
    <table id="objects-table">
      <tr>
        <th>Users</th>
        <th>Artwork</th>
        <th>Orders</th>
        <th>Shipments</th>
        <th>Payments</th>
      </tr>

      <tr>
        <td>
          <table id="user-table">
            <tr>
              <th>Username</th>
            </tr>
            <tr v-for="(user, i) in users" v-bind:key="`user-${i}`">
              <td>{{ user.username }}</td>
            </tr>
          </table>
        </td>

        <td>
          <table id="artwork-table">
            <tr>
              <th>Title</th>
              <th>Artwork ID</th>
            </tr>
            <tr v-for="(artwork, i) in artworks" v-bind:key="`artwork-${i}`">
              <td>{{ artwork.title }}</td>
              <td>{{ artwork.artworkId }}</td>
            </tr>
          </table>
        </td>

        <td>
          <table id="order-table">
            <tr>
              <th>Order ID</th>
              <th>Customer</th>
              <th>Artwork Title</th>
              <th>Artwork ID</th>
            </tr>
            <tr v-for="(order, i) in orders" v-bind:key="`order-${i}`">
              <td>{{ order.orderId }}</td>
              <td>{{ order.customer.username }}</td>
              <td>{{ order.artwork.title }}</td>
              <td>{{ order.artwork.artworkId }}</td>
            </tr>
          </table>
        </td>

        <td>
          <table>
            <tr>
              <th>Shipment ID</th>
              <th>Address ID</th>
              <th>Shipping Address</th>
            </tr>
            <tr v-for="(shipment, i) in shipments" v-bind:key="`shipment-${i}`">
              <td>{{ shipment.shipmentId }}</td>
              <td>{{ shipment.destinationAddress.addressId }}</td>
              <td>{{ shipment.destinationAddress.streetAddress }}</td>
            </tr>
          </table>
        </td>

        <td>
          <table>
            <tr>
              <th>Payment ID</th>
              <th>Card Number</th>
            </tr>
            <tr v-for="(payment, i) in payments" v-bind:key="`payment-${i}`">
              <td>{{ payment.paymentId }}</td>
              <td>{{ payment.cardNumber }}</td>
            </tr>
          </table>
        </td>
      </tr>

      <hr />
    </table>

    <span v-if="errorUsers" style="color: red">Error: {{ errorUsers }} </span>
    <span v-if="errorArtworks" style="color: red"
      >Error: {{ errorArtworks }}</span
    >
    <span v-if="errorOrders" style="color: red">Error: {{ errorOrders }} </span>
    <span v-if="errorAddress" style="color: red"
      >Error: {{ errorAddress }}
    </span>
    <hr /> -->
    <div><h1>Checkout</h1></div>

    <div class="container">
      <div class="box-1" v-if="!orderId && !errorCheckout">
        <img class="art-img" :src="artwork.imageUrl" />
        <!--<div class="artist">{{ artwork.artists[0] }}</div>-->
      </div>

      <div class="box-2" v-if="!orderId && !errorCheckout">
        <div class="title">{{ artwork.title }}</div>
        <p>Subtotal: ${{ artwork.price }}</p>
        <p>Taxes: ${{ tax }}</p>
        <div class="total">Total: ${{ total }}</div>

        <!--shipping-->
        <div class="shipping">
          <button
            id="toggle-shipping"
            textContent=" + SHIPPING"
            v-on:click="showShipping"
          >
            + SHIPPING
          </button>
        </div>
        <div class="shipping" id="shipping" style="display:none">
          <Shipment
            v-if="!shipmentId"
            v-bind:address="shippingAddress"
            v-on:add-shipment="getShipment($event)"
          />
        </div>

        <!--payment-->
        <div class="payment">
          <button
            id="toggle-payment"
            textContent=" + PAYMENT"
            v-on:click="showPayment"
          >
            + PAYMENT
          </button>
        </div>
        <div class="payment" id="payment" style="display:none">
          <Payment
            v-if="shipmentId && !paymentId"
            v-bind:payment="payment"
            v-on:add-payment="getPayment($event)"
          />
        </div>
      </div>
    </div>

    <form
      @submit="placeOrder(username, artworkId)"
      v-if="!orderId && !errorCheckout"
      id="checkout"
    >
      <!--  -->

      <div v-if="shipmentId && paymentId">
        <h4>Review Order</h4>
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

        <!-- FOR TESTING REMOVE WHEN STYLING -->
        <label>User: {{ username }} </label>
        <label>Artwork: {{ artworkId }} </label>
        <!-- -->

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
  width: 100%;
  height: auto;
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

button:hover {
  background-color: #000000;
  color: #e9e7db;
}
</style>
