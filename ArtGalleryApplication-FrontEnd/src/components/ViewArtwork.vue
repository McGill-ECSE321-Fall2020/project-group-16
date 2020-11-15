<template>
    <div id="viewartwork">

        <img src="../assets/no-img.png" alt="artwork image" style="width:500px, height:200px">
        <h1>
            {{artwork.title}}
        </h1>
        <h2>
            {{artwork.dimensions}}
            {{artwork.medium}}
            ${{artwork.price}}
        </h2>
        <h3>
            {{artwork.collection}}
            {{artwork.creationDate}}
        </h3>
        <body>
            {{artwork.description}}
        </body>
        <button @click="$router.push({ path: `/checkout/${theCurrentUser.username}/${artwork.artworkId}` })">
            Purchase
        </button>    
    </div>
</template>

<script src="../js/viewArtwork.js">
</script>

<style>

</style>