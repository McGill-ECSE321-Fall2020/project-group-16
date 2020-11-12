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
        width: 50%;
        height: 50%;
        flex-direction: column;
        border: 2px so
        lid black;
        padding: 10px;
    }

    .row {
        border: 2px solid red;
        padding: 10px;
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }

    .col-1 {
        border: 2px solid blue;
        padding: 10px;
        flex: 3;
    }

    .col-2 {
        border: 2px solid blue;
        padding: 10px;
        flex: 1;

        text-align: right;
    }
</style>