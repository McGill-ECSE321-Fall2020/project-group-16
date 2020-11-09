<template>
  <div id="place-order">
    <hr />
    <h2>Objects</h2>
    <table id="objects-table">
      <tr>
        <th>Users</th>
        <th>Artwork</th>
        <th>Orders</th>
        <th>Addresses</th>
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
              <th>Address ID</th>
              <th>Street</th>
            </tr>
            <tr v-for="(address, i) in addresses" v-bind:key="`address-${i}`">
              <td>{{ address.addressId }}</td>
              <td>{{ address.streetAddress }}</td>
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

    <h4>Return Address</h4>
    <CreateAddress v-on:add-address="addAddress($event)" />
    <h4>Shipping Address</h4>
    <CreateAddress v-on:add-address="addAddress($event)" />

    <!-- <h2>Purchase Artwork</h2>
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
    <button
      v-bind:disabled="!selectedUser || !selectedArtwork"
      @click="placeOrder(selectedUser, selectedArtwork)"
    >
      Place Order
    </button>
    <span v-if="errorPlaceOrder" style="color: red"
      >Error: {{ errorPlaceOrder }}
    </span> -->
    <hr />
    <hr />
  </div>
</template>

<script src="./placeOrder.js"></script>

<style>
#purchaseartwork {
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
