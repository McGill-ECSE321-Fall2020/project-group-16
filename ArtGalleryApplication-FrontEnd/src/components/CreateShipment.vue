<template>
  <div id="create-shipment">
    <hr />
    <h2>Shipments</h2>
    <table>
      <tr>
        <th>Shipment ID</th>
        <th>Return Address</th>
        <th>Shipping Address</th>
      </tr>
      <tr v-for="(shipment, i) in shipments" v-bind:key="`shipment-${i}`">
        <td>{{ shipment.shipmentId }}</td>
        <td>{{ shipment.returnAddress.streetAddress }}</td>
        <td>{{ shipment.destinationAddress.streetAddress }}</td>
      </tr>
    </table>

    <hr />
    <h2>Addresses</h2>
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

    <hr />
    <h2>Create Shipment</h2>
    <hr />

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
    <hr />
    <br />
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
