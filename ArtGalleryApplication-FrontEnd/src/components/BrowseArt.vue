<template>
  <div id="browse-art">
    <div class="filters">
      <div>
        <label>Status:</label>
        <select>
          <option value="All">All</option>
          <option value="For Sale">For Sale</option>
          <option value="Not For Sale">Not for Sale</option>
          <option value="Sold">Sold</option>
        </select>
      </div>

      <div>
        <label>Price range:</label>
        <input type="text" id="minprice" name="minprice" /> to
        <input type="text" id="maxprice" name="maxprice" />
      </div>

      <div>
        <label>Date created: </label>
        between
        <input type="date" id="mindate" name="mindate" /> and
        <input type="date" id="maxdate" name="maxdate" />
      </div>

      <div>
        <button v-on:click="filter()">FILTER</button>
      </div>
    </div>

    <div class="artproducts">
      <div
        class="grid-item"
        v-bind:key="artwork.id"
        v-for="artwork in artworks"
      >
        <ArtProduct
          v-bind:artworkId="artwork.artworkId"
          v-bind:title="artwork.title"
          v-bind:artists="artwork.artists"
          v-bind:creationDate="artwork.creationDate"
          v-bind:artworkStatus="artwork.artworkStatus"
          v-bind:price="artwork.price"
          v-bind:imageUrl="artwork.imageUrl"
        />
      </div>
    </div>
  </div>
</template>

<script src="../js/browseArt.js"></script>

<style>
#brows-eart {
  font-family: Montserrat;
  font-style: normal;
  padding: 60px;
  display: flex;
  flex-direction: column;
  overflow: scroll;
}

.artproducts {
  display: grid;
  grid-gap: 50px;
}

@media (min-width: 600px) {
  .artproducts {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 900px) {
  .artproducts {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (min-width: 1400px) {
  .artproducts {
    grid-template-columns: repeat(4, 1fr);
  }
}

.filters {
  display: flex;
  padding-bottom: 30px;
  justify-content: space-evenly;
}

.col {
  flex-grow: 1;
}

p {
  font-family: Montserrat;
  font-style: italic;
  font-weight: normal;
  font-size: 18px;
  line-height: 22px;
  color: #000000;
}

select {
  background-color: #e9e7db;
  color: #000000;
}

input {
  background-color: #e9e7db;
  border: 1px solid black;
}
input[type="text"] {
  width: 70px;
}
input[type="date"] {
  width: 160px;
}

.container {
  width: 290px;
  height: 320px;
  border: 1px solid black;
  padding: 10px;

  display: flex;
  flex-direction: column;

  justify-content: center;
  align-items: center;
}

.my-row {
  padding: 10px;
  display: flex;
  flex-direction: row;
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
