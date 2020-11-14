import { AXIOS } from './axiosInstance'

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
        AXIOS.get(`/artworks/${this.$route.params.artworkId}`)
            .then(response => {
                console.log(response)
                this.artwork = response.data;
                console.log(this.artwork)
            })
            .catch(function(e) {
                console.log(e)
                console.log(e.response);
            });
        console.log("mounted")

    },
    mounted: function() {
        // AXIOS.get(`/artworks/${this.artwork.artworkId}`)
        //     .then(response => {
        //         this.artwork = response.data[0];
        //         console.log(this.artwork)
        //     })
        //     .catch(e => {
        //         console.log(e);
        //     });
    }
};