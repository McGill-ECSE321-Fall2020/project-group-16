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
        <SignUpForm @update:user="updateUser" />
      </div>
      <div v-else-if="form === 'login'">
        <LoginForm @update:user="updateUser" />
      </div>
    </div>
  </div>
</template>
<script>
import SignUpForm from "./SignUpForm";
import LoginForm from "./LoginForm";
export default {
  name: "LandingPage",
  components: {
    SignUpForm,
    LoginForm,
  },
  data() {
    return {
      form: "signup",
      username: "",
      isLoggedIn: false,
    };
  },
  methods: {
    updateUser: function (username) {
      console.log("Update user in landing")
      this.username = username;
      this.isLoggedIn = true;
    },
  },
  watch: {
    isLoggedIn: function () {
      console.log("Emit in Landing")
      this.$emit("update:status", {
        username: this.username,
        isLoggedIn: this.isLoggedIn,
      });
    },
  },
};
</script>
<style scoped src="../css/landing.css">

</style>