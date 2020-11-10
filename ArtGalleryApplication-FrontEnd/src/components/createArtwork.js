import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default{
    name: "CreateArtwork", 
    data(){
        return {
            artworks: [], 
            newArtwork: {
                title: '',
                description: '',
                dimensions: '',
                medium: '', 
                collection: '',
                creationDate: '', 
                price: '', 
                imageUrl: ''
            },
            errorArtwork: '',
            response: []
        }
    },
    methods: {
        createArtwork: function (title, description, dimensions, medium, collection, creationDate, price, imageUrl){
            AXIOS.post('/artworks/?title={this.title}', {
                title: this.title,
                description: this.description,
                dimensions: this.dimensions, 
                medium: this.medium, 
                collection: this.collection, 
                creationDate: this.creationDate, 
                price: this.price, 
                imageUrl: this.imageUrl
            })
            .then((response)=> {
                 // JSON responses are automatically parsed.
                this.artworks.push(response.data);
                this.errorArtwork = "";
                this.newArtwork.title = '';
                this.newArtwork.description = '';
                this.newArtwork.dimensions = '';
                this.newArtwork.medium = '';
                this.newArtwork.collection = '';
                this.newArtwork.creationDate = '';
                this.newArtwork.price = '';
                this.newArtwork.imageUrl = '';
                })
            .catch(e => {
                var errorMsg = e.response.data.message;
                console.log(errorMsg);
                this.errorArtwork = errorMsg;
            });
        }

    }}