<template>
  <div>
    <div v-if="!deleted" id="viewartwork">
      <div class="box-1">
        <div class="outline">
          <img :src="artwork.imageUrl" alt="artwork image" />
        </div>
      </div>

      <div class="box-2">
        <p class="title">{{ artwork.title }}</p>
        <p class="artist" v-if="artwork.artists[0]">
          {{ artwork.artists[0].firstName }} {{ artwork.artists[0].lastName }}
        </p>
        <p class="date">Created: {{ artwork.creationDate }}</p>
        <p class="detail">Collection: {{ artwork.collection }}</p>
        <p class="detail">Dimensions: {{ artwork.dimensions }}</p>
        <p class="detail">Medium: {{ artwork.medium }}</p>
        <p class="descrip">Description: {{ artwork.description }}</p>

        <p>
          <button
            v-if="!userOwnsArtwork && artwork.artworkStatus === 'ForSale'"
            class="purchase"
            @click="
              $router.push({
                path: `/checkout/${theCurrentUser.username}/${artwork.artworkId}`,
              })
            "
          >
            PURCHASE - ${{ artwork.price.toFixed(2) }}
          </button>
        </p>
        <div v-if="artwork.artworkStatus !== 'ForSale'">
          <span
            v-if="artwork.artworkStatus === 'NotForSale'"
            class="badge badge-pill"
            style="background: #F69896; color: white; font-size: 24px"
            >Not For Sale</span
          >
          <span
            v-else-if="artwork.artworkStatus === 'Sold'"
            class="badge badge-pill"
            style="background: #6DCFF6; color: white; font-size: 24px"
            >Sold</span
          >
        </div>
        <br v-if="artwork.artworkStatus !== 'ForSale'" />

        <button
          v-if="!userOwnsArtwork"
          @click="
            $router.push({ path: `/user/${artwork.artists[0].username}` })
          "
        >
          More From This Artist
        </button>
        <button
          v-if="userOwnsArtwork && artwork.artworkStatus !== 'Sold'"
          @click="deleteArtwork(artwork.artworkId)"
        >
          Delete Artwork
        </button>
      </div>
    </div>

    <div v-if="deleted">
      <h1>Artwork Deleted</h1>
    </div>
  </div>
</template>

<script src="../js/viewArtwork.js"></script>

<style scoped>
#viewartwork {
  padding: 30px;
  display: flex;
}

.box-1 {
  display: flex;
  flex: 1;
  align-items: center;
  justify-content: center;
  padding: 10px;
}

img {
  width: 100%;
  height: auto;
  padding: 30px;
  border: 1px solid #000000;
}

.box-2 {
  text-align: left;
  flex: 1;
  padding: 10px;
}

.title {
  font-family: Montserrat;
  font-style: normal;
  font-weight: 600;
  font-size: 48px;
  line-height: 60px;
  color: #000000;
}

.artist {
  font-family: Montserrat;
  font-style: normal;
  font-weight: normal;
  font-size: 48px;
  line-height: 60px;
  color: #000000;
}

.date {
  font-family: Montserrat;
  font-style: italic;
  font-weight: normal;
  font-size: 24px;
  line-height: 30px;

  color: #000000;
}

.detail {
  font-family: Montserrat;
  font-style: italic;
  font-weight: normal;
  font-size: 18px;
  line-height: 24px;

  color: #000000;
}

.descrip {
  font-family: Montserrat;
  font-style: italic;
  font-weight: normal;
  font-size: 18px;
  line-height: 24px;

  color: #000000;
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

.purchase {
  background: #006F45;
  color: #E9E7DB;
}
</style>>
