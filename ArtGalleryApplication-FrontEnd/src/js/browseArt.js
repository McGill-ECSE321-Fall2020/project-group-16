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
      console.log(document.getElementById("maxprice").value);
      console.log(document.getElementById("mindate").value);
      console.log(document.getElementById("maxdate").value);
      console.log(document.getElementById('status').value);
      status = document.getElementById('status').value;
      AXIOS.get(`/artworks/byArtworkStatus/${status}`).then(response => {
        this.artwork = response.data[0];
        console.log(this.artwork);
      })
      .catch(e => {
        console.log(e);
      });
    }
  }
  
};
