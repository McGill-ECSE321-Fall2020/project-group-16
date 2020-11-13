import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: 'Access-Control-Allow-Origin: *' 
})

export default{
    name: "ViewArtowrk",
    data(){
        return{
            artworkId:'',
            artwork:'',
        };
    },
    mounted: function(){
        this.artworkId = this.$router.params;
    },
    created: function(){
        AXIOS.get(`/artworks/${this.artworkId}`)
      .then((response) => {
        this.artwork = response.data[0];
      })
      .catch((e) => {
        console.log(e);
      });
    }
}