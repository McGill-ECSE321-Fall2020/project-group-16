<template>
  <div id="art-product">
    <div class="container">
      <div class="row-1">
        <img id="art-img" :src="imageUrl" />
      </div>
      <div class="row-2">
        <div class="title">{{ title }}</div>
      </div>
      <div class="row-2">
        <div class="artist" v-if="artists[0]">
          {{ artists[0].firstName }} {{ artists[0].lastName }}
        </div>
      </div>
      <div class="row-2">
        <div class="date">{{ creationDate }}</div>
      </div>
      <div class="row-2">

        <span
          v-if="artworkStatus === 'ForSale'"
          class="badge badge-pill"
          style="background: #006F45; color: white"
          >${{ price }}</span
        >
        <span
          v-else-if="artworkStatus === 'NotForSale'"
          class="badge badge-pill"
          style="background: #F69896; color: white"
          >Not For Sale</span
        >
        <span
          v-else-if="artworkStatus === 'Sold'"
          class="badge badge-pill"
          style="background: #6DCFF6; color: white"
          >Sold</span
        >
      </div>
      <div class="row-1">
        <button @click="$router.push({ path: `/artworks/${artworkId}` })">
          VIEW ARTWORK
        </button>
      </div>
    </div>
  </div>
</template>

<script src="../js/artProduct.js">
</script>

<style scoped>
.container {
  width: 290px;
  height: 350px;
  border: 1px solid black;
  padding: 10px;

  display: flex;
  flex-direction: column;
}

.row-1 {
  padding: 10px;
  align-self: center;
}

.row-2 {
  padding-left: 10px;
  padding-right: 10px;
  align-self: start;
}

.button {
  cursor: pointer;
  border: 1px solid black;
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

.for-sale {
  height: 22px;
  width: 55px;
  background: #006F45;
  border-radius: 3px;

  font-family: Lora;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 18px;
  text-align: center;

  color: #E9E7DB;
}

button {
  width: 140px;
  height: 30px;
  border: 1px solid #000000;
  background-color: #e9e7db;
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
  color: #e9e7db;
}
</style>
