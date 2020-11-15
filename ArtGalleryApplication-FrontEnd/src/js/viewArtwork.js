import { AXIOS } from "./axiosInstance.js";

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
                status: "ForSale",
            },
            theCurrentUser: {
                username: "",
                admin: "",
            },

            errorCurrentUser: ''
        };
    },
    created: function() {
        this.artwork.artworkId = this.$route.params.artworkId;
        console.log(this.artwork.artworkId);
    },
    mounted: function() {
        AXIOS.get(`/artworks/${this.artwork.artworkId}`)
            .then(response => {
                this.artwork = response.data;
                console.log(this.artwork);
            })
            .catch(e => {
                console.log(e);
            });
        
        AXIOS.get("/users/".concat(localStorage.getItem('username')))
            .then((response) => {
                this.theCurrentUser = response.data;
                console.log(this.theCurrentUser.username)
            }).catch(function (err) {
                console.log(err.response);
                this.errorCurrentUser = "Error: " + err.response.data.message;
            });
    }
};