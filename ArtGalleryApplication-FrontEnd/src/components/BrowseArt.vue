<template>
    <div id="browseart"> 
        <!-- <div>
            <div v-bind:key="artwork.id" v-for="artwork in artworks">
              <ArtProduct v-bind:artwork="artwork" v-on:goto-artwork="$emit('goto-artwork', artwork.id)" />
            </div>
        </div>
        -->

        <div class="container">
            <div class="row">
                row 1
            </div>
            <div class="row">
                <div class="col-1">
                    row 2 col 1
                </div>
                <div class="col-2">
                    row 2 col 2
                </div>
            </div>
            <div class="row">
                <button>hi</button>
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
        padding: 60px;
        display: flex;
    }

    .container {
        width: 300px;
        height: 400px;
        border: 2px solid black;
        padding: 10px;
        
        display: flex;
        flex-direction: column;
    }

    .row {
        border: 2px solid red;
        padding: 10px;
        
        display: flex;
        flex-direction: row;
        justify-content: center;
    }

    .col-1 {
        border: 2px solid blue;
        padding: 10px;
        text-align: left;
    }

    .col-2 {
        border: 2px solid blue;
        padding: 10px;

        text-align: right;
    }
</style>