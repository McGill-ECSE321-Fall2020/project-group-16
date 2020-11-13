<template>
  <div class="profile-info p-3">
      <img class="profile-image" src="../assets/Profile Pic.png" alt="">
      <h2 class="name">{{user.firstName}}&nbsp;{{user.lastName}}</h2>
      <h3>{{user.username}}</h3><br>
      <p><b>Email: </b>{{user.email}}</p>
      <p><b>Description: </b>{{checkDescription()}}</p>
      <div class="edit-button py-1 px-2 mx-auto">EDIT PROFILE</div>
  </div>
</template>

<script src="../js/userProfileInfo.js">

</script>

<style scoped src="../css/profileInfo.css">
</style>