<template>
    <div id="viewartwork">
        <img src="../assets/no-img.png" alt="artwork image" width="600" height="600">
        <h1>
            {{this.artwork.title}}
        </h1>
        <h2>
            {{artwork.dimensions}}
            {{artwork.medium}}
            ${{artwork.price}}
        </h2>
        <h3>
            {{this.collection}}
            {{this.creationDate}}
        </h3>
        <body>
            {{this.description}}
        </body>
    </div>
</template>

<script src="../js/viewArtwork.js">
</script>

<style>

</style>