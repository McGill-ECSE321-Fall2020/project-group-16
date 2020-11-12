<template>
  <div id="app">
    <!-- <img src="./assets/logo.png"> -->
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
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
