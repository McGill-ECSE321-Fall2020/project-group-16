<template>
  <div id="app" v-bind:class="{ landing: $route.path === '/' }">
    <NavBar v-if="$route.path !== '/'" />
    <router-view
      v-bind:class="{ content: $route.path !== '/' }"
      @update:status="logIn"
    ></router-view>
  </div>
</template>

<script src="./js/app.js">
</script>

<style>
@import "./css/main.css";

#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  height: 100vh;
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: #E9E7DB;
}

.content {
  width: 80%;
  position: relative;
  top: 115px;
  background: #E9E7DB;
}

.landing {
  overflow-x: hidden;
  overflow-y: hidden;
}
@font-face {
  font-family: "Lora";
  src: local("Lora"), url(./fonts/Lora/Lora-Regular.ttf)format("truetype");
}

@font-face {
  font-family: "Montserrat";
  src: local("Montserrat"),
    url(./fonts/Montserrat/Montserrat-Regular.ttf)format("truetype");
}
</style>
