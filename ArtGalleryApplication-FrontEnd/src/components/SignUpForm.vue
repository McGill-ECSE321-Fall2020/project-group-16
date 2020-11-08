<template>
  <div id="sign-up-form">
    <div class="name">
      <span
        ><label for="first-name">First Name</label><br /><input
          type="text"
          name="first-name"
          id="first-name"
          v-model="firstName"
      /></span>
      <span
        ><label for="last-name">Last Name</label><br />
        <input type="text" name="last-name" id="last-name" v-model="lastName"
      /></span>
    </div>

    <label for="username">Username</label><br />
    <input type="text" name="username" id="username" v-model="username" /><br />
    <label for="email">Email</label><br />
    <input type="email" name="email" id="email" v-model="email" /><br />
    <label for="password">Password</label><br />
    <input
      type="password"
      name="password"
      id="password"
      v-model="password"
    /><br /><br />

    <div class="button" v-on:click="signup()">Sign Up</div>
  </div>
</template>
<script>
import Axios from "axios";
export default {
  name: "SignUpForm",
  data() {
    return {
      username: "",
      password: "",
      firstName: "",
      lastName: "",
      email: "",
      user: "",
      errorUser: "",
    };
  },
  methods: {
    signup: function signup() {
      alert(this.username + this.email);
      AXIOS.post("/users/".concat(username), {
        params: {
          firstName: firstName,
          lastName: lastName,
          email: email,
          password: password,
        },
      })
        .then((response) => {
          this.user = response.data;
          console.log(this.user);
        })
        .catch((e) => {
          this.errorUser = e;
          console.log(this.errorUser);
        });
      alert(this.user);
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
.name {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
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