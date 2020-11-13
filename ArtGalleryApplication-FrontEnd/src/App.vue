<template>
  <div id="app">
    <NavBar v-if="$route.path !== '/'"/>
    <router-view @update:status="updateStatus"></router-view>
  </div>
</template>

<script>
import NavBar from './components/NavBar'
export default {
  name: "app",
  components: {NavBar,},
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
      console.log("Watch worked")
      if (this.isLoggedIn) {
        console.log("Updated");
        localStorage.setItem("username", this.username);
        console.log(localStorage.getItem("username"))
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
@import './css/main.css';

#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  height: 100vh;
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
} 

@font-face {
  font-family: "Lora";
  src: local("Lora"), 
  url(./fonts/Lora/Lora-Regular.ttf) format("truetype");
}

@font-face {
  font-family: "Montserrat";
  src: local("Montserrat"), 
  url(./fonts/Montserrat/Montserrat-Regular.ttf) format("truetype");
}

</style>
