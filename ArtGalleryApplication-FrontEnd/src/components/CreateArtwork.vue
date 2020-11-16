<template>
  <div id="createartwork">
    <h1 style="text-align: center">Upload your artwork</h1>

    <div class="mx-auto my-4" style="text-align: center; width: fit-content">
      <div v-if="newArtwork.imageUrl === ''">
        <h3>Upload a photo</h3>
        <div class="button px-3 py-2" @click="click1">CHOOSE A PHOTO</div>
      </div>
      <div v-else style="width: fit-content; position: relative">
        <img :src="newArtwork.imageUrl" alt="" />
        <div class="button py-2 px-3 my-4 mx-auto" @click="click1">
          CHANGE PHOTO
        </div>
      </div>
      <input
        type="file"
        ref="input1"
        style="display: none"
        @change="previewImage"
        accept="image/*"
      />
    </div>
    <input
      type="text"
      name=""
      id=""
      placeholder="Title"
      v-model="newArtwork.title"
    />
    <div class="d-none d-sm-block"></div>
    <textarea
      name=""
      id=""
      cols="30"
      rows="10"
      placeholder="Description"
      v-model="newArtwork.description"
    ></textarea
    ><br />
    <input
      type="text"
      name=""
      id=""
      placeholder="Dimensions (eg: 10'x10')"
      v-model="newArtwork.dimensions"
    />
    <br />
    <input
      type="text"
      name=""
      id=""
      placeholder="Art medium (eg: Paint)"
      v-model="newArtwork.medium"
    />
    <br />
    <input
      type="text"
      name=""
      id=""
      placeholder="Art Collection (or n/a)"
      v-model="newArtwork.collection"
    />
    <br />
    <label>Creation date:</label>
    <input
      type="date"
      name=""
      id=""
      placeholder="YYYY-MM-DD"
      v-model="newArtwork.creationDate"
    />
    <br />
    <label>Price: $</label>
    <input
      type="number"
      name=""
      id=""
      placeholder="XX.XX"
      v-model="newArtwork.price"
    />
    <div
      v-if="errorArtwork !== ''"
      class="alert alert-warning alert-dismissible fade show mx-auto"
      role="alert"
      style="width: fit-content"
    >
      {{ errorArtwork }}
      <button
        type="button"
        class="close"
        data-dismiss="alert"
        aria-label="Close"
      >
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div
      v-if="
        newArtwork.title !== '' &&
        newArtwork.description !== '' &&
        newArtwork.imageUrl !== '' &&
        newArtwork.price !== '' &&
        newArtwork.dimensions !== '' &&
        newArtwork.collection !== ''
      "
      class="button px-3 py-2 my-4"
      v-on:click="
        createArtwork(
          newArtwork.title,
          newArtwork.description,
          newArtwork.creationDate,
          newArtwork.imageUrl,
          newArtwork.price,
          newArtwork.status,
          newArtwork.dimensions,
          newArtwork.collection
        );
        create();
      "
    >
      Post Artwork
    </div>
    <div
      v-else
      class="alert alert-danger mx-auto my-4"
      style="width: fit-content"
      role="alert"
    >
      Please fill out all the fields.
    </div>
  </div>
</template>

<script src= "../js/createArtwork.js">
</script>

<style scoped src="../css/createArtwork.css">
</style>