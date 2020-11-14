import AXIOS from "./axiosInstance.js";

export default {
  name: "ViewArtwork",
  data() {
    return {
      artwork: {
        artworkId: "",
        title: "",
        description: "",
        dimensions: "",
        medium: "",
        collection: "",
        creationDate: "",
        price: "",
        imageUrl: "",
        status: "ForSale"
      }
    };
  },
  created: function() {
    this.artwork.artworkId = this.$route.params;
    console.log(artworkId);
  },
  mounted: function() {
    AXIOS.get(`/artworks/${this.artwork.artworkId}`)
      .then(response => {
        this.artwork = response.data[0];
        console.log(this.artwork);
      })
      .catch(e => {
        console.log(e);
      });
  }
};
