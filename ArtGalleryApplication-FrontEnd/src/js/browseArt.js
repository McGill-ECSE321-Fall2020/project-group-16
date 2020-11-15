import { AXIOS } from "./axiosInstance";
import ArtProduct from "../components/ArtProduct.vue";

export default {
  name: "BrowseArt",
  components: {
    ArtProduct
  },

  data() {
    return {
      artworks: [],
      filteredArtworks: [],

      filter: {
        status: 'All',
        minPrice: '',
        maxPrice: '',
        from: '',
        to: ''
      },

      sortBy: ''
    };
  },

  created: function () {
    AXIOS.get("/artworks")
      .then(response => {
        this.artworks = response.data;
        this.filteredArtworks = response.data;
      })
      .catch(e => {
        this.errorArtworks = e;
      });
  },

  methods: {
    filterArtworks() {
      this.filteredArtworks = []

      for (var i = 0; i < this.artworks.length; i++) {
        var artwork = this.artworks[i]

        if (this.filter.status !== "All" && artwork.artworkStatus !== this.filter.status) {     // Status
          continue
        }

        if (this.filter.minPrice && artwork.price < this.filter.minPrice) {                     // Min Price
          continue
        }
        if (this.filter.maxPrice && artwork.price > this.filter.maxPrice) {                     // Max Price
          continue
        }

        if (this.filter.from) {                               // From Date
          var fromDate = new Date(this.filter.from);
          var artworkDate = new Date(artwork.creationDate)

          if (fromDate > artworkDate) {
            continue
          }
        }
        if (this.filter.to) {                                 // To Date
          var toDate = new Date(this.filter.to);
          var artworkDate = new Date(artwork.creationDate)

          if (toDate < artworkDate) {
            continue
          }
        }

        this.filteredArtworks.push(artwork)
      }

      if (this.sortBy) {
        this.sort()
      }
    },
    resetFilters() {
      this.filter.status = "All"
      this.filter.minPrice = ''
      this.filter.maxPrice = ''
      this.filter.from = ''
      this.filter.to = ''
      this.sortBy = ''

      this.filteredArtworks = this.artworks
    },
    sort() {
      if (this.sortBy === "PriceInc") {
        this.filteredArtworks.sort(function (a, b) {
          var keyA = a.price
          var keyB = b.price
          // Compare the 2 dates
          if (keyA < keyB) return -1;
          if (keyA > keyB) return 1;
          return 0;
        });
      } else if (this.sortBy === "PriceDec") {
        this.filteredArtworks.sort(function (a, b) {
          var keyA = a.price
          var keyB = b.price
          // Compare the 2 dates
          if (keyA < keyB) return 1;
          if (keyA > keyB) return -1;
          return 0;
        });
      } else if (this.sortBy === "DateInc") {
        this.filteredArtworks.sort(function (a, b) {
          var keyA = new Date(a.creationDate)
          var keyB = new Date(b.creationDate)
          // Compare the 2 dates
          if (keyA < keyB) return -1;
          if (keyA > keyB) return 1;
          return 0;
        });
      } else if (this.sortBy === "DateDec") {
        this.filteredArtworks.sort(function (a, b) {
          var keyA = new Date(a.creationDate)
          var keyB = new Date(b.creationDate)
          // Compare the 2 dates
          if (keyA < keyB) return 1;
          if (keyA > keyB) return -1;
          return 0;
        });
      }
    }
  }

};
