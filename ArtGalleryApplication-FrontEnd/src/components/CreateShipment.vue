<template>
  <div id="create-shipment">
    <h4>Shipment</h4>
    <CreateAddress
      title="Return Address"
      v-on:add-address="addAddress($event, 'return')"
    />
    <CreateAddress
      title="Shipping Address"
      v-on:add-address="addAddress($event, 'destination')"
    />

    <form
      @submit="
        createShipment(
          newShipment.toGallery,
          newReturnAddress.addressId,
          newDestAddress.addressId
        )
      "
    >
      <input type="checkbox" v-model="newShipment.toGallery" />
      {{ newShipment.toGallery ? "Shipped to Gallery" : "Delivered" }}
      <br />
      <input
        v-bind:disabled="
          !newDestAddress.addressId || !newReturnAddress.addressId
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
