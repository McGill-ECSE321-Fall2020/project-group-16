<template>
    <div id="browseart"> 
        <div>
            <h1>Browse Artwork</h1>
            <div v-bind:key="artwork.id" v-for="artwork in artworks">
              <ArtProduct v-bind:artwork="artwork" v-on:goto-artwork="$emit('goto-artwork', artwork.id)" />
            </div>
        </div>
    </div>
</template>

<script>
import ArtProduct from './ArtProduct.vue';
export default {
    name: "BrowseArt",
    components: {
        ArtProduct
    },
    props: ["artworks"]
}
</script>

<style>
  #browseart {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>