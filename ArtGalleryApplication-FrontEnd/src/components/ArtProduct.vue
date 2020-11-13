<template>
  <div id="art-product">
    <div class="container">
      <div class="row-1">
        <img id="art-img" :src="imageUrl">
        </div>
      <div class="row-2">
        <div class="title">{{title}}</div>
      </div>
      <div class="row-2">
        <div class="artist">{{artists[0]}}</div>
      </div>
      <div class="row-2">
        <div class="date">{{creationDate}}</div>
      </div>
      <div class="row-2">
        <div class="for-sale">${{price}}</div>
      </div>
      <div class="row-1">
        <button @click="$router.push({ path: `/artworks/${artworkId}` }), router.params">VIEW ARTWORK</button>
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
