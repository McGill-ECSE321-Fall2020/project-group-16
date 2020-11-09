<template>
  <div id="checkout">
    <hr />
    <h2>TEST</h2>
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
    </table>

    <hr />
    <hr />

    <h2>Shipping Address</h2>
    <table>
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

    <hr />
    <h2>Return Address</h2>
    <table>
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

<script src="./checkout.js"></script>

<style>
#checkout {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>
