<template>
  <div id="shipment">
    <h4>Shipment</h4>
    <form @submit="createShipment(toGallery, address)">
      <input type="checkbox" v-model="toGallery" @click="updateAddresses" />
      {{ toGallery ? "Shipped to Gallery" : "Delivered" }}
      <br />

      <Address
        title="Shipping Address"
        v-bind:inputDisabled="toGallery"
        v-bind:address="address"
        v-on:add-address="addAddress($event, 'destination')"
      />

      <input
        v-bind:disabled="
          !toGallery &&
            (!address.streetAddress ||
              !address.postalCode )
        "
        type="submit"
        value="Continue"
        class="btn"
      />
    </form>

    <hr />
  </div>
</template>

<script src="../js/shipment.js"></script>

<style>
#shipment {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>
