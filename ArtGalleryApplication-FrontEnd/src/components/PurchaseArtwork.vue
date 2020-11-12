<template>
  <div id="purchaseartwork">
    <hr />
    <h2>Users</h2>
    <table id="user_table">
      <tr>
        <th>Username</th>
        <!-- <th>Events</th> -->
      </tr>
      <tr v-for="(user, i) in users" v-bind:key="`user-${i}`">
        <td>{{ user.username }}</td>
      </tr>
    </table>
    <span v-if="errorUsers" style="color: red">Error: {{ errorUsers }} </span>
    <hr />

    <h2>Artwork</h2>
    <table id="artwork_table">
      <tr>
        <th>Title</th>
        <th>Artwork ID</th>
      </tr>
      <tr v-for="(artwork, i) in artworks" v-bind:key="`artwork-${i}`">
        <td>{{ artwork.title }}</td>
        <td>{{ artwork.artworkId }}</td>
      </tr>
    </table>
    <span v-if="errorArtworks" style="color: red"
      >Error: {{ errorArtworks }}
    </span>
    <hr />

    <h2>Orders</h2>
    <table id="order_table">
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
    <span v-if="errorOrders" style="color: red">Error: {{ errorOrders }} </span>
    <hr />

    <h2>Purchase Artwork</h2>
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
    </span>
    <hr />
    <hr />
  </div>
</template>

<script src = "../js/purchaseArtwork.js"></script>

<style>
#purchaseartwork {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>
