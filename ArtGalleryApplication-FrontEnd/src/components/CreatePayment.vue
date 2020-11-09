<template>
  <div id="create-payment">
    <hr />
    <h2>Payments</h2>
    <table>
      <tr>
        <th>Card Number</th>
        <th>Expiration Date</th>
        <th>CVV</th>
        <th>Payment Date</th>
        <th>Payment Time</th>
        <th>Payment Form</th>
        <!--<th>Edit</th>-->
      </tr>
      <tr v-for="(payment, i) in payments" v-bind:key="`payment-${i}`">
        <td>{{ payment.cardNumber }}</td>
        <td>{{ payment.expirationDate }}</td>
        <td>{{ payment.cvv }}</td>
        <td>{{ payment.paymentDate }}</td>
        <td>{{ payment.paymentTime }}</td>
        <td>{{ payment.paymentForm }}</td>
        <!--<td>
        <button v-on:click="updateEvent(event.name)">Edit</button>
      </td>-->
      </tr>
      <tr>
        <td>
          <input
            type="text"
            v-model="newPayment.cardNumber"
            placeholder="Card Number"
          />
        </td>
        <td>
          <input
            type="text"
            v-model="newPayment.expirationDate"
            placeholder="MM/YY"
          />
        </td>
        <td>
          <input type="text" v-model="newPayment.cvv" placeholder="CVV" />
        </td>

        <td>
          <button
            v-bind:disabled="
              !newPayment.cardNumber ||
                !newPayment.cvv ||
                !newPayment.expirationDate
            "
            v-on:click="
              createPayment(
                newPayment.cardNumber,
                newPayment.cvv,
                newPayment.expirationDate
              )
            "
          >
            Create
          </button>
        </td>
      </tr>
    </table>

    <span v-if="errorPayment" style="color: red"
      >Error: {{ errorPayment }}
    </span>
    <hr />
  </div>
</template>

<script src="./createPayment.js"></script>

<style>
#createaddress {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>
