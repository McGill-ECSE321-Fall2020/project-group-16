import { AXIOS } from "./axiosInstance";
import ArtProduct from "../components/ArtProduct.vue";

export default {
  name: "BrowseArt",
  components: {
    ArtProduct
  },

    /**
     * declaration of the page's data
     */
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

  /**
   * Page initialization on page creation
   */
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

    /**
     * Filter the artworks using customizable filters
     */
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

    /**
     * Reset the filters, on button click
     */
    resetFilters() {
      this.filter.status = "All"
      this.filter.minPrice = ''
      this.filter.maxPrice = ''
      this.filter.from = ''
      this.filter.to = ''
      this.sortBy = ''

      this.filteredArtworks = this.artworks
    },

    /**
     * Sort the artworks based on the filters
     */
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
    },

    /**
     * Show filters selected by the user, to the user
     */
    showFilters() {
      var show = document.getElementById("filters")
      var text = document.getElementById("toggle-filters");
 
      if(show.style.display == "none") {
        show.style.display = "block";
      } else {
        show.style.display = "none";
      }

      if(text.textContent == " + SHOW FILTERS") {
        text.textContent = " - HIDE FILTERS";
      }
      else {
        text.textContent = " + SHOW FILTERS";
      }
    }
  }

};
