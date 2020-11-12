<template>
  <div id="createaddress">
    <hr />
    <h2>Addresses</h2>
    <table>
      <tr>
        <th>Street</th>
        <th>Street 2</th>
        <th>Postal Code</th>
        <th>City</th>
        <th>Province</th>
        <th>Country</th>
        <!--<th>Edit</th>-->
      </tr>
      <tr v-for="(address, i) in addresses" v-bind:key="`address-${i}`">
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
      <tr>
        <td>
          <input
            type="text"
            v-model="newAddress.streetAddress"
            placeholder="Street Address 1"
          />
        </td>
        <td>
          <input
            type="text"
            v-model="newAddress.streetAddress2"
            placeholder="Street Address 2"
          />
        </td>
        <td>
          <input
            type="text"
            v-model="newAddress.postalCode"
            placeholder="Postal Code"
          />
        </td>
        <td>
          <input type="text" v-model="newAddress.city" placeholder="City" />
        </td>
        <td>
          <select type="text" v-model="newAddress.province">
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
          <select type="text" v-model="newAddress.country">
            <option>Canada</option>
            <option>United States</option>
          </select>
        </td>
        <td>
          <button
            v-bind:disabled="
              !newAddress.streetAddress || !newAddress.postalCode
            "
            v-on:click="
              createAddress(
                newAddress.streetAddress,
                newAddress.streetAddress2,
                newAddress.postalCode,
                newAddress.city,
                newAddress.province,
                newAddress.country
              )
            "
          >
            Create
          </button>
        </td>
      </tr>
    </table>
    <span v-if="errorAddress" style="color: red"
      >Error: {{ errorAddress }}
    </span>
    <hr />
  </div>
</template>

<script src = "../js/createAddress.js"></script>

<style>
#createaddress {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>