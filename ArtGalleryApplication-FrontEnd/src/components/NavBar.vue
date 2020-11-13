<template>
  <div id="nav-bar">
      <h1 class="title ml-5 py-2">Art Gallery</h1>
      <div class="nav-tabs row mr-5">
          <div class="nav-tab col-sm px-4 py-2 align-middle" id="home" v-bind:class="{active: $route.path === '/home'}" v-on:click="changeTab">Artwork</div>
          <div class="nav-tab col-sm px-4 py-2 align-middle" id="events" v-bind:class="{active: $route.path === '/events'}" v-on:click="changeTab">Events</div>
          <div class="nav-tab col-sm px-4 py-2 align-middle" id="user" v-bind:class="{active: $route.path === `/user/${username}`}" v-on:click="changeTab">My Profile</div>

      </div>
  </div>
</template>

<script>
export default {
    name: "NavBar",
    data() {
        return {
            username: localStorage.getItem("username")
        }
    },
    methods: {
        changeTab: function (e) {
            console.log(e)
            if (e.target.id === 'user'){this.$router.push({path: `/${e.target.id}/${this.username}`})
            } else {
                this.$router.push({path: `/${e.target.id}`})
            }
            

        }
    }
    

}
</script>

<style scoped src="../css/navBar.css">

</style>