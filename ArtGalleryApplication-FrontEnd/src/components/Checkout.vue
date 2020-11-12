<template>
  <div>
    <hr />
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
    <hr />


    <form @submit="placeOrder(selectedUser, selectedArtwork)" id="checkout">
      <h2>Checkout</h2><hr />

      <!--  -->
      <Shipment v-if="!shipmentId"  v-bind:address="shippingAddress" v-on:add-shipment="getShipment($event)" />
      <Payment v-if="shipmentId && !paymentId" v-bind:payment="payment" v-on:add-payment="getPayment($event)" />

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

        <label
          >User:
          <select v-model="selectedUser">
            <option disabled value="">Select a User</option>
            <option v-for="(user, i) in users" v-bind:key="`user-${i}`">
              {{ user.username }}
            </option>
          </select>
        </label>
        <label
          >Artwork:
          <select type="number" v-model="selectedArtwork">
            <option disabled value="">Select an Artwork</option>
            <option v-for="(artwork, i) in artworks" v-bind:key="`artwork-${i}`">
              {{ artwork.artworkId }}
            </option>
          </select>
        </label>
        <input
          type="submit"
          value="PlaceOrder"
          class="btn"
          v-bind:disabled="
            !selectedUser || !selectedArtwork || !shipmentId || !paymentId
          "
        />
      </div>

      <span v-if="errorCheckout" style="color: red"
      >Error: {{ errorCheckout }}
      </span>
    </form>
    
    <hr />

  </div>
</template>

<script src="../js/checkout.js"></script>

<style>
#checkout {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
table th,
td {
  border: 1px solid black;
  padding: 10px;
}
</style>