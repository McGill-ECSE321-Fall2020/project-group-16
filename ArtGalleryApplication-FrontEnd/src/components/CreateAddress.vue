<template>
  <div id="createaddress">
    <br />
    <form>
      <h6>{{ title }}</h6>
      <input
        type="text"
        v-model="address.streetAddress"
        name="StreetAddress"
        placeholder="Street Address 1"
        :disabled="inputDisabled"
      />

      <input
        type="text"
        v-model="address.streetAddress2"
        placeholder="Street Address 2"
        :disabled="inputDisabled"
      />

      <input
        type="text"
        v-model="address.postalCode"
        placeholder="Postal Code"
        :disabled="inputDisabled"
      />

      <input
        type="text"
        v-model="address.city"
        placeholder="City"
        :disabled="inputDisabled"
      />

      <select type="text" v-model="address.province" :disabled="inputDisabled">
        <option disabled value="">Province</option>
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

      <select type="text" v-model="address.country" :disabled="inputDisabled">
        <option disabled value="">Country</option>
        <option>Canada</option>
        <option>United States</option>
      </select>
    </form>
    <br />
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
