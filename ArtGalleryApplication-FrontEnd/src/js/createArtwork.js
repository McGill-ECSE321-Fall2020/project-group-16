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
            AXIOS.post("/artworks/".concat(newArtwork.atworkId), {}, {
                params: {
                    title: newArtwork.title,
                    description: newArtwork.description,
                    creationDate: newArtwork.creationDate,
                    medium: newArtwork.medium,
                    imageUrl: newArtwork.imageUrl,
                    price: newArtwork.price,
                    status: newArtwork.status,
                    dimensions: newArtwork.dimensions,
                    collection: newArtwork.collection,
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