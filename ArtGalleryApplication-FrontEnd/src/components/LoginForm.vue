<template>
  <div id="login-form">
    <label for="username">Username</label><br />
    <input type="text" name="username" id="username" v-model="username" /><br />
    <label for="password">Password</label><br />
    <input
      type="password"
      name="password"
      id="password"
      v-model="password"
    /><br /><br />
    <div class="button" v-on:click="login">Login</div>
  </div>
</template>
<script>
import { AXIOS } from "./axiosInstance";
export default {
  name: "SignUpForm",
  data() {
    return {
      username: "",
      password: "",
      user: "",
      errorUser: "",
    };
  },
  methods: {
    login: function () {
      AXIOS.get(`/users/${this.username}?password=${this.password}`)
        .then((response) => {
          this.user = response.data;
          this.$emit("update:user", this.user.username);
        })
        .catch((e) => {
          this.errorUser = e;
          console.log(this.errorUser);
        });
    },
  },
};
</script>
<style scoped>
input {
  border: none;
  border-radius: 15px;
  padding: 5px;
  width: 100%;
}
.button {
  padding: 5px;
  border-radius: 10px;
  background-image: linear-gradient(to right, red, orange);
  width: max-content;
  text-align: center;
  color: white;
  margin: auto;
  height: max-content;
  font-size: 1.3em;
  font-style: bold;
  cursor: pointer;
}
</style>
