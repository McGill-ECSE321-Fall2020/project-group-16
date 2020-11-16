<template>
  <div id="browse-art">
      <div class="black-button px-3 py-2" @click="$router.push({ path: `/create_artwork` })">
        CREATE ARTWORK
      </div>
      <br>
    <div class="filters">
      <button
        id="toggle-filters"
        textContent=" + SHOW FILTERS"
        v-on:click="showFilters"
      >
        + SHOW FILTERS
      </button>
    </div>
    <div class="filters" id="filters" style="display: none">
      <div class="filters-row">
        <div class="filter-item">
          <label>Sort By:</label>
          <select v-model="sortBy">
            <option disabled value="">Sort By</option>
            <option value="PriceInc">Price: Low - High</option>
            <option value="PriceDec">Price: High - Low</option>
            <option value="DateDec">Newest</option>
            <option value="DateInc">Oldest</option>
          </select>
        </div>

        <div class="filter-item">
          <label>Status:</label>
          <select v-model="filter.status">
            <option disabled value="">Select Artwork Status</option>
            <option value="All">All</option>
            <option value="ForSale">For Sale</option>
            <option value="NotForSale">Not for Sale</option>
            <option value="Sold">Sold</option>
          </select>
        </div>

        <div class="filter-item">
          <label>Price range:</label>
          <input
            type="text"
            v-model="filter.minPrice"
            placeholder="Min Price"
            id="minPrice"
            name="minPrice"
          />
          to
          <input
            type="text"
            v-model="filter.maxPrice"
            placeholder="Max Price"
            id="maxPrice"
            name="maxPrice"
          />
        </div>

        <div class="filter-item">
          <label>Date created: </label>
          between
          <input
            type="date"
            v-model="filter.from"
            id="mindate"
            name="mindate"
          />
          and
          <input type="date" v-model="filter.to" id="maxdate" name="maxdate" />
        </div>
      </div>
      <div class="filters-row">
        <div class="filter-item">
          <button v-on:click="filterArtworks">FILTER</button>
        </div>
        <div class="filter-item">
          <button v-on:click="resetFilters">RESET FILTERS</button>
        </div>
      </div>
    </div>

    <div class="artproducts pb-5">
      <div
        class="grid-item"
        v-bind:key="artwork.id"
        v-for="artwork in filteredArtworks"
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
#browse-art {
  font-family: Montserrat;
  font-style: normal;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.artproducts {
  display: grid;
  grid-gap: 40px;
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

#togglefilters {
  padding: 10px;
}

.filters {
  display: flex;
  flex-direction: column;
  padding-bottom: 30px;
  justify-content: space-evenly;
}

.filters-row {
  display: flex;
  flex-direction: row;
}

.filter-item {
  padding: 5px;
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
  width: 80px;
}
input {
  background-color: #e9e7db;
  border: 1px solid black;
}
input[type="text"] {
  width: 80px;
}
input[type="date"] {
  width: 150px;
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

.black-button {
    width: fit-content;
    background-color: black;
    text-align: center;
    cursor: pointer;
    transition: 0.5s;
    color: white;
}

.black-button:hover {
    background-color: #006F45;
}
</style>
