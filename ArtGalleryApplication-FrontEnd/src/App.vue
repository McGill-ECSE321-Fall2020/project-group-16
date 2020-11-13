<template>
  <div id="app" v-bind:class="{landing: $route.path === '/'}">
    <NavBar v-if="$route.path !== '/'"/>
    <router-view v-bind:class="{content: $route.path !== '/'}" @update:status="updateStatus"></router-view>
  </div>
</template>

<script src="./js/app.js">

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

.content {
  width: 80%;
  top: 200px;
  position: relative;
}

.landing {
  overflow-x: hidden;
  overflow-y: hidden;
}
</style>
