<template>
    <div id="createartwork" >

        <h1>Upload your artwork</h1>
        <div>            
            <div>
                <div >
                Upload a photo
                <button @click="click1">choose a photo</button>
                <input type="file" ref="input1"
                    style="display: none"
                    @change="previewImage" accept="image/*" >                
                </div>
        
                <div>                  
                    <img v-if="imageData!=null" class="preview" height="268" width="356" :src="img1">
                <br>
                </div> 
            </div>    

        </div>

        <table>
        <tr>
          <td>
              Artwork Title:
          </td>
          <td>
              <input 
              type="text"
              v-model = "newArtwork.title"
              placeholder="Title"
              :disabled="artworkCreated  == 'true'">
          </td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.description"
            placeholder="describe your art here"
            :disabled="artworkCreated  == 'true'">
            </td>
        </tr>
        <tr>
            <td>Dimensions:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.dimensions"
            placeholder="10x10"
            :disabled="artworkCreated  == 'true'">
            </td>
        </tr>
        <tr>
            <td>Medium:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.medium"
            placeholder="paint"
            :disabled="artworkCreated  == 'true'">
            </td>
        </tr>
        <tr>
            <td>Collection:</td>
            <td><input 
            type="text"
            v-model = "newArtwork.collection"
            placeholder="n/a or collection name"
            :disabled="artworkCreated  == 'true'">
            </td>
        </tr>
        <tr>
            <td>Creation Date:</td>
            <td><input 
            type="date"
            v-model = "newArtwork.creationDate"
            placeholder="YYYY-MM-DD"
            :disabled="artworkCreated  == 'true'">
            </td>
        </tr>
        <tr>
            <td>Price (in $CAD):</td>
            <td><input 
            type="number"
            v-model = "newArtwork.price"
            placeholder="1000"
            :disabled="artworkCreated  == 'true'">
            </td>
        </tr>
    </table>
    <p>
      <span v-if="errorArtwork" style="color: red"> {{ errorArtwork }} </span>
    </p>
    <button
    v-bind:disabled="!newArtwork.title || artworkCreated == 'true'" 
    v-on:click="createArtwork(
        newArtwork.title, 
        newArtwork.description, 
        newArtwork.creationDate,
        newArtwork.imageUrl,
        newArtwork.price,
        newArtwork.status,
        newArtwork.dimensions, 
        newArtwork.collection
        ); create()">
    Post Artwork
    </button>

    </div>
</template>

<script src= "../js/createArtwork.js">
</script>

<style>
    #createartwork{
        font-family: Montserrat;
        font-style: normal;
        font-weight: normal;
    }
    input{
        background-color: #e9e7db;
    }
    button {
        width: 300px;
        height: 40px;
        border-radius: 3px;
        font-family: Montserrat;
        font-style: normal;
        font-weight: normal;
        font-size: 18px;
        line-height: 24px;
        text-align: center;
        transition: 0.5s;
    }

    button:hover {
        background-color: #000000;
        color: #E9E7DB;
    }
</style>