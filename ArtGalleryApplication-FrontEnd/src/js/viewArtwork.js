import axios from "axios";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: "Access-Control-Allow-Origin: *"
});

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
  mounted: function() {
    this.artworkId = this.$router.params;
    console.log(artworkId);
  },
  created: function() {
    AXIOS.get(`/artworks/${this.artworkId}`)
      .then(response => {
        this.artwork = response.data[0];
      })
      .catch(e => {
        console.log(e);
      });
  }
};
