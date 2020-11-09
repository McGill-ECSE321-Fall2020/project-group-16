<template>
  <div id="createaddress">
    <hr />
    <form @submit="createAddress">
      <input
        type="text"
        v-model="streetAddress"
        name="StreetAddress"
        placeholder="Street Address 1"
      />

      <input
        type="text"
        v-model="streetAddress2"
        placeholder="Street Address 2"
      />

      <input type="text" v-model="postalCode" placeholder="Postal Code" />

      <input type="text" v-model="city" placeholder="City" />

      <select type="text" v-model="province">
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

      <select type="text" v-model="country">
        <option>Canada</option>
        <option>United States</option>
      </select>

      <input
        v-bind:disabled="!streetAddress || !postalCode"
        type="submit"
        value="Submit"
        class="btn"
      />
    </form>

    <hr />
  </div>
</template>

<script src="./createAddress.js"></script>

<style>
#createaddress {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>
