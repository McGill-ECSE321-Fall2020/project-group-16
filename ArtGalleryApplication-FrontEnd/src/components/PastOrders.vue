<template>
  <div class="order-container">
      <h1 class="pt-4">PAST ORDERS</h1>
      <h2 v-if="this.orders.length === 0">You have no past orders</h2>
      <div v-else class="orders"><div class="order" v-for="order in orders" v-bind:key="order.id">
        <h1>{{order.title}}</h1>
        <h2>{{order.customer.firstName}}&nbsp;{{order.customer.lastName}}</h2>
        <p>Purchased on: {{order.orderDate}}</p>
        <div>View Artwork</div>
        </div>
        </div>
  </div>
</template>

<script src="../js/pastOrders">

</script>

<style scoped src="../css/pastOrders.css">

</style>