<template>
  <div id="landing-page" class="container">
    <h1>Welcome to the gallery!</h1>
    <h2>Login or signup to view community artwork.</h2>
    <div class="sign-in-form">
      <div class="form-tabs">
        <h2
          v-bind:class="{ active: form === 'login' }"
          v-on:click="form = 'login'"
        >
          Login
        </h2>
        <h2
          v-bind:class="{ active: form === 'signup' }"
          v-on:click="form = 'signup'"
        >
          Sign Up
        </h2>
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
<style scoped>
.sign-in-form {
  display: flex;
  flex-direction: column;
  width: 35%;
  margin: 30px auto;
  background-color: #dddddd;
  padding: 15px;
  border-radius: 25px;
  text-align: left;
}
.form-tabs {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
.form-tabs h2 {
  cursor: pointer;
}

.active {
  color: #ff8c00;
  border-bottom: 3px solid #ff8c00;
}
</style>