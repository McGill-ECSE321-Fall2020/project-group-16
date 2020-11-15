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
            type="date"
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
    <p>
      <span v-if="errorArtwork" style="color: red"> {{ errorArtwork }} </span>
    </p>
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

    <br>
    <br>
    <div>
        <h2>Add Artists to the artwork {{newArtwork.title}}:</h2>
        <table>
            <tr>
                <td><label>Add artist</label></td>
                <td>
                    <label> Select the user: 
                    <select v-model="artistToAdd">
                        <option value="">username</option>
                        <option v-for="user in allUsers" v-bind:key="user.username">{{ user.username }}</option>
                    </select>
                    </label>
                </td>
                <td>
                    <button v-bind:disabled="!artistToAdd" @click="addArtist()">Add artist</button>
                </td>
            </tr>
        </table>

        <table>
            <p>
            <span v-if="errorAllUsers" style="color: red"> {{ errorAllUsers }} </span>
            </p>
            <tr>List of added artists:</tr>
            <tr v-for="artist in newArtwork.artists" v-bind:key="artist.username">
                {{ artist.username }}
            </tr>
            <p>
            <span v-if="errorAddArtist" style="color: red"> {{ errorAddArtist }} </span>
            </p>
        </table>
    </div>

    <br>
    <br>
    <button @click="reloadPage()">Done</button>
    
    </div>
</template>

<script src= "../js/createArtwork.js">
</script>

<style>

</style>