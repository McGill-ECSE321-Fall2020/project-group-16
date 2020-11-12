<template>
    <div id="browseart"> 
        <ArtProduct/>
        
        <!-- <div>
            <div v-bind:key="artwork.id" v-for="artwork in artworks">
              <ArtProduct v-bind:artwork="artwork" v-on:goto-artwork="$emit('goto-artwork', artwork.id)" />
            </div>
        </div>
        -->
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
        font-family: Montserrat;
        font-style: normal;
        padding: 60px;
        display: flex;
    }

    .container {
        width: 290px;
        height: 320px;
        border: 1px solid black;
        padding: 10px;
        
        display: flex;
        flex-direction: column;

        justify-content: center;
        align-items: center;
    }

    .my-row {
        padding: 10px;
        display: flex;
        flex-direction: row;
    }

    img {
        height: 160px;
    }

    .title {
        font-weight: 600;
        font-size: 18px;
        line-height: 22px;
    }

    .artist {
        font-weight: normal;
        font-size: 18px;
        line-height: 22px;
    }

    .date {
        font-weight: normal;
        font-size: 18px;
        line-height: 22px;
    }

    button {
        width: 140px;
        height: 30px;

        border: 1px solid #000000;
        background-color: #E9E7DB;
        color: #000000;
        box-sizing: border-box;
        border-radius: 3px;

        font-weight: normal;
        font-size: 14px;
        line-height: 17px;
        text-align: center; 

        transition: 0.5s;
    }

    button:hover {
        background-color: #000000;
        color: #E9E7DB;
    }

</style>