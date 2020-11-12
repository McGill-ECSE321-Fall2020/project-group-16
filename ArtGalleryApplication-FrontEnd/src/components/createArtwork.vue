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
              v-model = "newArtwork.title"
              placeholder="Title">
          </td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.description"
            placeholder="describe your art here">
            </td>
        </tr>
        <tr>
            <td>Dimensions:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.dimensions"
            placeholder="10x10">
            </td>
        </tr>
        <tr>
            <td>Medium:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.medium"
            placeholder="paint">
            </td>
        </tr>
        <tr>
            <td>Collection:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.collestion"
            placeholder="n/a or collection name">
            </td>
        </tr>
        <tr>
            <td>Creation Date:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.creationDate"
            placeholder="YYYY-MM-DD">
            </td>
        </tr>
        <tr>
            <td>Price (in $CAD):</td>
            <td><input 
            type="text"
            v-model = "newArtwork.price"
            placeholder="1000">
            </td>
        </tr>
        <tr>
            <td>Image URL:</td>
            <td><input 
            type="text"
            v-model = newArtwork.imageUrl
            placeholder="www.">
            </td>
        </tr>
    </table>
    <button
    v-bind:disabled="!newArtwork.title" 
    v-on:click="createArtwork(
        newArtwork.title, 
        newArtwork.description, 
        newArtwork.creationDate,
        newArtwork.imageUrl,
        newArtwork.price,
        newArtwork.status,
        newArtwork.dimensions, 
        newArtwork.collection
        )">
    Add Artwork
    </button>
    
    </div>
</template>

<script src= "./createArtwork.js">
</script>

<style>

</style>