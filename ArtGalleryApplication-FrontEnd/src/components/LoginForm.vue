<template>
  <div id="login-form">
    <form action=""><div class="row my-3 px-3">
    <input type="text" name="username" id="username" placeholder="Username" v-model="username" /><br /></div>
    <div class="row my-3 px-3">
    <input
      type="password"
      name="password"
      id="password"
      placeholder="Password"
      v-model="password"
    /></div>
    <div class="button px-3 py-1 mt-1 mb-3" v-on:click="login">Login</div>
    </form>
    
  </div>
</template>
<script src="../js/login.js">

</script>
<style scoped src="../css/signInForm.css">

</style>
