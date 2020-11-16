<template>
  <div id="profile-page">
    <!-- <div class="row info-orders">
      <div class="col px-0 col-4" v-bind:class="{'offset-4': !currentUser}"><UserProfileInfo v-bind:user="user" v-bind:currentUser="currentUser"/></div>
      <div class="col col-7 offset-1 px-0"><PastOrders v-if="currentUser" v-bind:username="username"/></div>
    </div> -->
    <div v-bind:class="{ 'info-orders': currentUser, 'mx-auto': !currentUser }">
      <div class="">
        <UserProfileInfo v-bind:user="user" v-bind:currentUser="currentUser" />
      </div>
      <div class="">
        <PastOrders v-if="currentUser" v-bind:username="username" />
      </div>
    </div>
    <div class="mx-auto py-3 seperator"></div>

    <div
      style="display: flex; align-items: center"
      v-bind:style="[
        currentUser
          ? { 'justify-content': 'space-between' }
          : { 'justify-content': 'center' },
      ]"
    >
      <h1 class="py-3" v-bind:class="{ center: !currentUser }">
        {{ currentUser ? "YOUR" : "THEIR" }} ARTWORK
      </h1>
      <div
        v-if="currentUser"
        class="black-button px-3 py-2"
        @click="$router.push({ path: `/create_artwork` })"
        style="height: fit-content"
      >
        CREATE ARTWORK
      </div>
    </div>

    <div
      class="artwork-section my-4"
      v-bind:style="{
        justifyContent: currentUser ? 'space-between' : 'center',
      }"
    >
      <div class="artproducts">
        <div
          class="grid-item"
          v-bind:key="artwork.id"
          v-for="artwork in userArtworks"
        >
          <ArtProduct
            v-bind:artworkId="artwork.artworkId"
            v-bind:title="artwork.title"
            v-bind:artists="artwork.artists"
            v-bind:creationDate="artwork.creationDate"
            v-bind:artworkStatus="artwork.artworkStatus"
            v-bind:price="artwork.price"
            v-bind:imageUrl="artwork.imageUrl"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script src="../js/profilePage.js">
</script>
<style scoped src="../css/profilePage.css">
</style>
