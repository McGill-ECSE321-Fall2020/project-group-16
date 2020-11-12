<template>
  <div class="container">
    <UserProfileInfo />
  </div>
</template>
<script>
import { AXIOS } from "./registration";
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
