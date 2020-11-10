<template>
    <div id="createartwork">
        <h1>Upload your artwork</h1>
        <table>
        <tr>
          <td>
              Artwork Title:
          </td>
          <td>
              <input 
              type="text"
              v-model = "newArtwork.title">
          </td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.description">
            </td>
        </tr>
        <tr>
            <td>Dimensions:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.dimensions">
            </td>
        </tr>
        <tr>
            <td>Medium:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.medium">
            </td>
        </tr>
        <tr>
            <td>Collection:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.collestion">
            </td>
        </tr>
        <tr>
            <td>Creation Date:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.creationDate">
            </td>
        </tr>
        <tr>
            <td>Price (in $CAD):</td>
            <td><input 
            type="text"
            v-model = "newArtwork.price">
            </td>
        </tr>
        <tr>
            <td>Image URL:</td>
            <td><input 
            type="text"
            v-model = newArtwork.imageUrl>
            </td>
        </tr>
    </table>
    <div class = "button" @click="createArtwork"> Add Artwork </div>
    </div>
</template>

<script src= "./createArtwork.js">
</script>

<style>

</style>