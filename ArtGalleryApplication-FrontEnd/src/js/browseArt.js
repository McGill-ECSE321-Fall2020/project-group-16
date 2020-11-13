import { AXIOS } from "./axiosInstance";
import ArtProduct from "../components/ArtProduct.vue";

export default {
  name: "BrowseArt",
  components: {
    ArtProduct
  },

  data() {
    return {
      artworks: ""
    };
  },

  created: function() {
    AXIOS.get("/artworks")
      .then(response => {
        this.artworks = response.data;
        console.log(this.artworks)
      })
      .catch(e => {
        this.errorArtworks = e;
      });
  },

  methods: {
    filter: function() {
      console.log(document.getElementById("minprice").value);
      //AXIOS.get(`/artworks/byArtworkStatus/${}`)
    }
  }
  
};
