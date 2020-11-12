<template>
  <div class="container">
    <UserProfileInfo />
    {{user.username}}
  </div>
</template>
<script>
import { AXIOS } from "../js/axiosInstance";
import UserProfileInfo from "./UserProfileInfo";
export default {
  name: "ProfilePage",
  data() {
    return {
      username: "",
      user: "",
    
    };
  },
  components: {
    UserProfileInfo,
  },
  created: function () {
    this.username = this.$route.params.username;
    console.log(this.username + "router")
    AXIOS.get(`/users/${this.username}`)
      .then((response) => {
        console.log(response.data)
        this.user = response.data;
      })
      .catch((e) => {
        console.log(e);
      });
  },
};
</script>
<style scoped></style>
