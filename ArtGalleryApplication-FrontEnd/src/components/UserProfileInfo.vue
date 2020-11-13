<template>
  <div class="p-3" id="profile-info">
      <img class="profile-image" src="../assets/Profile Pic.png" alt="">
      <h2 class="name">{{user.firstName}}&nbsp;{{user.lastName}}</h2>
      <h3>{{user.username}}</h3>
      <div v-if="currentUser" class="border-button py-1 px-2 mx-auto my-4" @click="logOut">LOGOUT</div>
      <p><b>Email: </b>{{user.email}}</p>
      <p><b>Description: </b>{{checkDescription()}}</p>
      <div v-if="currentUser" class="border-button py-1 px-2 mx-auto" @click="editProfile">EDIT PROFILE</div>
  </div>
</template>

<script src="../js/userProfileInfo.js">

</script>

<style scoped src="../css/profileInfo.css">
</style>