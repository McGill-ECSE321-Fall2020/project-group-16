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
        <!--<td>
        <button v-on:click="updateEvent(event.name)">Edit</button>
      </td>-->
      </tr>
    </table>

    <hr />
    <h2>Addresses</h2>
    <table>
      <tr>
        <th>Address ID</th>
        <th>Street</th>
        <th>Street 2</th>
        <th>Postal Code</th>
        <th>City</th>
        <th>Province</th>
        <th>Country</th>
        <!--<th>Edit</th>-->
      </tr>
      <tr v-for="(address, i) in addresses" v-bind:key="`address-${i}`">
        <td>{{ address.addressId }}</td>
        <td>{{ address.streetAddress }}</td>
        <td>{{ address.streetAddress2 }}</td>
        <td>{{ address.postalCode }}</td>
        <td>{{ address.city }}</td>
        <td>{{ address.province }}</td>
        <td>{{ address.country }}</td>
        <!--<td>
        <button v-on:click="updateEvent(event.name)">Edit</button>
      </td>-->
      </tr>
    </table>

    <span v-if="errorAddress" style="color: red"
      >Error: {{ errorAddress }}
    </span>

    <hr />
    <h2>Return Address</h2>
    <table v-if="!newReturnAddress.addressId">
      <tr>
        <td>
          <input
            type="text"
            v-model="newReturnAddress.streetAddress"
            placeholder="Street Address 1"
          />
        </td>
        <td>
          <input
            type="text"
            v-model="newReturnAddress.streetAddress2"
            placeholder="Street Address 2"
          />
        </td>
        <td>
          <input
            type="text"
            v-model="newReturnAddress.postalCode"
            placeholder="Postal Code"
          />
        </td>
        <td>
          <input
            type="text"
            v-model="newReturnAddress.city"
            placeholder="City"
          />
        </td>
        <td>
          <select type="text" v-model="newReturnAddress.province">
            <option>AB</option>
            <option>BC</option>
            <option>MB</option>
            <option>NB</option>
            <option>NL</option>
            <option>NT</option>
            <option>NS</option>
            <option>NU</option>
            <option>ON</option>
            <option>PE</option>
            <option>QB</option>
            <option>SK</option>
            <option>YT</option>
          </select>
        </td>
        <td>
          <select type="text" v-model="newReturnAddress.country">
            <option>Canada</option>
            <option>United States</option>
          </select>
        </td>
      </tr>
    </table>

    <span v-if="errorAddress" style="color: red"
      >Error: {{ errorAddress }}
    </span>
    <hr />

    <h2>Shipping Address</h2>
    <table v-if="!newDestAddress.addressId">
      <tr>
        <td>
          <input
            type="text"
            v-model="newDestAddress.streetAddress"
            placeholder="Street Address 1"
          />
        </td>
        <td>
          <input
            type="text"
            v-model="newDestAddress.streetAddress2"
            placeholder="Street Address 2"
          />
        </td>
        <td>
          <input
            type="text"
            v-model="newDestAddress.postalCode"
            placeholder="Postal Code"
          />
        </td>
        <td>
          <input type="text" v-model="newDestAddress.city" placeholder="City" />
        </td>
        <td>
          <select type="text" v-model="newDestAddress.province">
            <option>AB</option>
            <option>BC</option>
            <option>MB</option>
            <option>NB</option>
            <option>NL</option>
            <option>NT</option>
            <option>NS</option>
            <option>NU</option>
            <option>ON</option>
            <option>PE</option>
            <option>QB</option>
            <option>SK</option>
            <option>YT</option>
          </select>
        </td>
        <td>
          <select type="text" v-model="newDestAddress.country">
            <option>Canada</option>
            <option>United States</option>
          </select>
        </td>
      </tr>
    </table>

    <span v-if="errorAddress" style="color: red"
      >Error: {{ errorAddress }}
    </span>
    <hr />

    <p>
      <input type="checkbox" v-model="newShipment.toGallery" />
      {{ newShipment.toGallery ? "Shipped to Gallery" : "Delivered" }}
    </p>
    <hr />
    <hr />

    <h2>Create Shipment</h2>
    <button
      v-if="!newDestAddress.addressId || !newReturnAddress.addressId"
      v-bind:disabled="
        !newReturnAddress.streetAddress ||
          !newReturnAddress.postalCode ||
          !newDestAddress.streetAddress ||
          !newDestAddress.postalCode
      "
      v-on:click="
        createAddress(newReturnAddress);
        createAddress(newDestAddress);
      "
    >
      Review Shipment Info
    </button>
    <button
      v-if="newDestAddress.addressId && newReturnAddress.addressId"
      v-on:click="
        createShipment(
          newShipment.toGallery,
          newReturnAddress.addressId,
          newDestAddress.addressId
        )
      "
    >
      Create Shipment
    </button>
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
