<template>
  <div id="nav-bar">
      <h1 class="title ml-5 py-2">Art Gallery</h1>
      <div class="nav-tabs row mr-5">
          <div class="nav-tab col-sm px-4 py-2 align-middle" v-bind:class="{active: curTab === 'artwork'}" v-on:click="curTab = 'artwork'"><h1>Artwork</h1></div>
          <div class="nav-tab col-sm px-4 py-2 align-middle" v-bind:class="{active: curTab === 'events'}" v-on:click="curTab = 'events'"><h1>Events</h1></div>
          <div class="nav-tab col-sm px-4 py-2 align-middle" v-bind:class="{active: curTab === 'profile'}" v-on:click="curTab = 'profiles'"><h1>My Profile</h1></div>

      </div>
  </div>
</template>

<script>
export default {
    name: "NavBar",
    data() {
        return {
            curTab: "artwork",
        }
        
    },
    

}
</script>

<style scoped src="../css/navBar.css">

</style>