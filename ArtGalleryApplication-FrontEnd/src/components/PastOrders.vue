<template>
  <div class="order-container" v-bind:style="getHeight()">
    <h1 style="text-align: center" class="py-4">PAST ORDERS</h1>
    <h2 v-if="this.orders.length === 0" class="px-3">
      You have no past orders
    </h2>
    <ul v-else class="list-unstyled orders mx-5">
      <li
        v-for="order in orders"
        v-bind:key="order.id"
        class="media mb-4 order p-2"
        @click="goToArtwork(order.artwork.artworkId)"
      >
        <img
          v-bind:src="order.artwork.imageUrl"
          alt=""
          class="align-content-center mr-3"
        />
        <div class="media-body" style="text-align: left, width:fit-content">
          <h5 class="mt-0 mb-1 mx-auto" style="">
            {{ order.artwork.title }}
          </h5>
          Purchased on: {{ order.orderDate }} <br />
          <span class="badge badge-pill badge-success">{{
            order.totalAmount.toFixed(2)
          }}</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script src="../js/pastOrders">
</script>

<style scoped src="../css/pastOrders.css">
</style>