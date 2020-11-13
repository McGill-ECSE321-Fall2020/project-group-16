<template>
  <div class="order-container" v-bind:style="getHeight">
      <div class="header"><h1 class="pt-4">PAST ORDERS</h1></div>
      <h2 v-if="this.orders.length === 0">You have no past orders</h2>
      <div v-else class="orders mx-4 "><div class="order py-3" v-for="order in orders" v-bind:key="order.id">
        <h1>{{order.title}}</h1>
        <h2>{{order.customer.firstName}}&nbsp;{{order.customer.lastName}}</h2>
        <p>Purchased on: {{order.orderDate}}</p>
        <div class="border-button py-1 px-2 mx-auto">View Artwork</div>
        </div>
        </div>
  </div>
</template>

<script src="../js/pastOrders">

</script>

<style scoped src="../css/pastOrders.css">

</style>