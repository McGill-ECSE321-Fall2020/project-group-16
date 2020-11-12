<template>
  <div id="app">
    <router-view @update:status="updateStatus"></router-view>
  </div>
</template>

<script>
export default {
  name: "app",
  components: {},
  data() {
    return {
      username: "",
      isLoggedIn: "",
    };
  },
  methods: {
    updateStatus: function (status) {
      console.log("Emit worked");
      this.username = status.username;
      this.isLoggedIn = status.isLoggedIn;
    },
  },
  watch: {
    isLoggedIn: function () {
      if (this.isLoggedIn) {
        console.log("Updated");
        localStorage.setItem("username", this.username);
        this.$router.push({
          name: "ProfilePage",
          params: { username: this.username },
        });
      }
    },
  },
};
</script>

<style>


#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  background: #E9E7DB;
  margin-top: 60px;
} 
</style>
