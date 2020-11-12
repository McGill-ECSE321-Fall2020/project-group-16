<template>
  <div class="art-product">
    <img src="" alt="" />
    <h2>{{ artwork.title }}</h2>
    <h3>{{ artwork.artist }}</h3>
    <h4>{{ artwork.creationDate }}</h4>
    <h4>{{ artwork.artworkStatus }}</h4>
    <button @click="$emit('goto-artwork', artwork.id)"></button>
  </div>
</template>

<script>
import { AXIOS } from "./axiosInstance.js";

export default {
  name: "ArtProduct",
  created() {
    axios
      .get("/artworks")
      .then(res => (this.artworks = res.data))
      .catch(err => console.log(err));
  }
};
</script>

<style scoped></style>
