<template>
  <div id="shipment">
    <h4>Shipment Information:</h4>
    <br />
    <form @submit="createShipment(toGallery, address)">
      <input type="checkbox" v-model="toGallery" @click="updateAddresses" />
      {{ toGallery ? "Shipped to Gallery" : "Delivered" }}

      <Address
        title="Shipping Address"
        v-bind:inputDisabled="toGallery"
        v-bind:address="address"
        v-on:add-address="addAddress($event, 'destination')"
      />

      <input
        v-bind:disabled="
          !toGallery && (!address.streetAddress || !address.postalCode)
        "
        type="submit"
        value="Continue"
        class="btn"
      />
    </form>
  </div>
</template>

<script src="../js/shipment.js"></script>

<style>
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
  color: black;
}
</style>
