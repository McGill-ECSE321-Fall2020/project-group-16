<template>
  <div id="nav-bar">
    <h1 class="title ml-5 py-2">Art Gallery 16</h1>
    <div
      class="nav-dropdown"
      @focus="displayDropdown()"
      @focusout="hideDropdown()"
      tabindex="0"
    >
      <i class="fas fa-bars fa-2x"></i>
      <div v-if="dropdownActive" class="dropdown-tabs">
        <div
          class="browseart dropdown-tab"
          v-bind:class="{
            'dropdown-active':
              $route.name === 'BrowseArt' || $route.name === 'ViewArtwork',
          }"
          @click="changeTab"
        >
          Artwork
        </div>
        <div
          class="events dropdown-tab"
          v-bind:class="{
            'dropdown-active':
              $route.name === 'GalleryEvents' ||
              $route.name === 'SpecificEvent',
          }"
          @click="changeTab"
        >
          Events
        </div>
        <div
          class="user dropdown-tab"
          v-bind:class="{
            'dropdown-active':
              $route.name === `ProfilePage` || $route.name === `EditProfile`,
          }"
          @click="changeTab"
        >
          My Profile
        </div>
      </div>
    </div>

    <div class="nav-tabs row mr-5">
      <div
        class="browseart nav-tab col-sm px-4 py-2"
        v-bind:class="{
          active: $route.name === 'BrowseArt' || $route.name === 'ViewArtwork',
        }"
        v-on:click="changeTab"
      >
        Artworks
      </div>
      <div
        class="events nav-tab col-sm px-4 py-2"
        v-bind:class="{
          active:
            $route.name === 'GalleryEvents' || $route.name === 'SpecificEvent',
        }"
        v-on:click="changeTab"
      >
        Events
      </div>
      <div
        class="user nav-tab col-sm px-4 py-2"
        v-bind:class="{
          active:
            $route.name === `ProfilePage` || $route.name === `EditProfile`,
        }"
        v-on:click="changeTab"
      >
        My Profile
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NavBar",
  props: ["showDropdown"],
  data() {
    return {
      username: localStorage.getItem("username"),
      dropdownActive: false,
    };
  },
  methods: {
    changeTab: function (e) {
      if (e.target.classList.contains("user")) {
        this.$router.push({ path: `/user/${this.username}` });
      } else if (e.target.classList.contains("browseart")) {
        this.$router.push({ path: `/browseart` });
      } else if (e.target.classList.contains("events")) {
        this.$router.push({ path: "/events" });
      }
      this.hideDropdown();
    },
    displayDropdown: function () {
      this.dropdownActive = true;
    },
    hideDropdown: function () {
      this.dropdownActive = false;
      document.activeElement.blur();
    },
  },
};
</script>

<style scoped src="../css/navBar.css">
</style>