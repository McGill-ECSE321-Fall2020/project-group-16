import { AXIOS } from './axiosInstance'

export default {
    name: "CreateArtwork",
    data() {
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
                imageUrl: '',
                status: 'ForSale'
            },
            errorArtwork: '',
            response: []
        }
    },
    created: function() {
        // Initializing persons from backend
        AXIOS.get("/artwork")
            .then(response => {
                // JSON responses are automatically parsed.
                this.artworks = response.data;
            })
            .catch(e => {
                this.errorAddress = e;
            });
    },

    methods: {
        createArtwork: function(title, description, creationDate, medium, imageUrl, price, status, dimension, collection) {
            console.log(
                this.newArtwork.title,
                this.newArtwork.description,
                this.newArtwork.dimensions,
                this.newArtwork.medium,
                this.newArtwork.collection,
                this.newArtwork.creationDate,
                this.newArtwork.price,
                this.newArtwork.imageUrl
            )
            AXIOS.post("/artworks/".concat(this.newArtwork.atworkId), {}, {
                params: {
                    title: this.newArtwork.title,
                    description: this.newArtwork.description,
                    creationDate: this.newArtwork.creationDate,
                    medium: this.newArtwork.medium,
                    imageUrl: this.newArtwork.imageUrl,
                    price: this.newArtwork.price,
                    status: this.newArtwork.status,
                    dimensions: this.newArtwork.dimensions,
                    collection: this.newArtwork.collection,
                }
            })

            .then(response => {
                // JSON responses are automatically parsed.
                console.log(newArtwork)
                this.artworks.push(response.data);
                this.errorArtwork = ''
                this.newArtwork.title = ''
                this.newArtwork.description = ''
                this.newArtwork.dimensions = ''
                this.newArtwork.medium = ''
                this.newArtwork.collection = ''
                this.newArtwork.creationDate = ''
                this.newArtwork.price = ''
                this.newArtwork.imageUrl = ''
            })

            .catch(e => {
                var errMsg = e.response.data.message
                console.log(errMsg);
                this.errorArtwork = errMsg;
            });
        }

    }
};