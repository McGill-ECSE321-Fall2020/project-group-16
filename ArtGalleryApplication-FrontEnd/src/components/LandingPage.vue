<template>
  <div id="landing-page" class="container">
    <h1 class="mb-3">Welcome to the gallery!</h1>
    <h2>Please <strong>sign up</strong> or <strong>log in</strong>  to see more.</h2>
    <div class="sign-in-form">
      <div class="form-tabs row">
        <div class="form-tab col-sm" v-bind:class="{ active: form === 'login' }"
          v-on:click="form = 'login'"><h2
          
        >
          Login
        </h2></div>
        <div class="form-tab col-sm" v-bind:class="{ active: form === 'signup' }"
          v-on:click="form = 'signup'"><h2
          
        >
          Sign Up
        </h2></div>
        
      </div>
      <div v-if="form === 'signup'">
        <SignUpForm @update:user="updateUser" @update:error="updateError"/>
      </div>
      <div v-else-if="form === 'login'">
        <LoginForm @update:user="updateUser" />
      </div>
    </div>
    <div class="alert alert-danger" v-if="error !== ''">{{error}}</div>
  </div>
</template>
<script src="../js/landing.js">

</script>
<style scoped src="../css/landing.css">

</style>