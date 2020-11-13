<template>
  <div id="profile-page">
    <div class="row info-orders">
      <div class="col px-0 col-4" v-bind:class="{'offset-4': !currentUser}"><UserProfileInfo v-bind:user="user" v-bind:currentUser="currentUser"/></div>
      <div class="col col-7 offset-1 px-0"><PastOrders v-if="currentUser" v-bind:username="username"/></div>
    </div>
    <div class="mx-auto py-3 seperator"></div>
    <div class="artwork-section mt-3" v-bind:style="{justifyContent: currentUser ? 'space-between' : 'center'}">
      <h1>{{currentUser ? "YOUR" : "THEIR"}} ARTWORK</h1>
      <div class="black-button py-2 px-4" v-if="currentUser">CREATE NEW ARTWORK</div>
    </div>
    
  </div>
</template>
<script src="../js/profilePage.js">

</script>
<style scoped src="../css/profilePage.css">

</style>
