<template>
  <div id="profile-page">
    <div class="row info-orders">
      <div class="col col-4 pl-0" mr-5><UserProfileInfo v-bind:user="user"/></div>
      <div class="col col-7 offset-1 pr-0"><PastOrders v-bind:username="username"/></div>
    </div>
    <div class="mx-auto py-3 seperator"></div>
    <div class="artwork-section mt-3">
      <h1>YOUR ARTWORK</h1>
      <div class="edit-button py-2 px-4">CREATE NEW ARTWORK</div>
    </div>
    
  </div>
</template>
<script src="../js/profilePage.js">

</script>
<style scoped src="../css/profilePage.css">

</style>
