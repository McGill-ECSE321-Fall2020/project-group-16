<template>
  <div id="create-shipment">
    <h4>Shipment</h4>
    <form @submit="createShipment(toGallery, newReturnAddress, newDestAddress)">
      <input type="checkbox" v-model="toGallery" @click="updateAddresses" />
      {{ toGallery ? "Shipped to Gallery" : "Delivered" }}
      <br />

      <CreateAddress
        title="Return Address"
        v-bind:inputDisabled="toGallery"
        v-bind:address="newReturnAddress"
        v-on:add-address="addAddress($event, 'return')"
      />
      <CreateAddress
        title="Shipping Address"
        v-bind:inputDisabled="toGallery"
        v-bind:address="newDestAddress"
        v-on:add-address="addAddress($event, 'destination')"
      />

      <input
        v-bind:disabled="
          !toGallery &&
            (!newDestAddress.streetAddress ||
              !newDestAddress.postalCode ||
              !newReturnAddress.streetAddress ||
              !newReturnAddress.postalCode)
        "
        type="submit"
        value="Create Shipment"
        class="btn"
      />
    </form>
  </div>
</template>

<script src="./createShipment.js"></script>

<style>
#createaddress {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>
