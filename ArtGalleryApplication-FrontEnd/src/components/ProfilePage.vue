<template>
  <div class="container">
    <div class="jumbotron">
      <span class="d-flex"
        ><h1>{{ user.firstName }}&nbsp;</h1>
        <h1>{{ user.lastName }}</h1></span
      >
      <h2>{{ user.username }}</h2>
      <h3>{{ user.email }}</h3>
    </div>
  </div>
</template>
<script>
import { AXIOS } from "./registration";
export default {
  name: "ProfilePage",
  data() {
    return {
      username: "",
      user: "",
    };
  },
  mounted: function () {
    this.username = this.$router.params;
  },
  created: function () {
    AXIOS.get(`/users/${this.username}`)
      .then((response) => {
        this.user = response.data[0];
      })
      .catch((e) => {
        console.log(e);
      });
  },
};
</script>
<style scoped></style>
