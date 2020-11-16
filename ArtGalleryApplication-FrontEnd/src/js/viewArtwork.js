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
                artists: [],
                imageUrl: "",
                status: "",
            },
            theCurrentUser: {
                username: "",
                admin: "",
            },

            userOwnsArtwork: false,
            deleted: false,

            errorCurrentUser: '',
            errorArtwork: ''
        };
    },
    created: function () {
        this.artwork.artworkId = this.$route.params.artworkId;
    },
    mounted: function () {
        AXIOS.get(`/artworks/${this.artwork.artworkId}`)
            .then(response => {
                this.artwork = response.data;
                this.isUserOwnerOfArtwork(response.data.artists)
            })
            .catch(e => {
                console.log(e);
            });

        AXIOS.get("/users/".concat(localStorage.getItem('username')))
            .then((response) => {
                this.theCurrentUser = response.data;
            }).catch(function (err) {
                console.log(err.response);
                this.errorCurrentUser = "Error: " + err.response.data.message;
            });
    },
    methods: {
        isUserOwnerOfArtwork(artists) {
            const curUsername = localStorage.getItem('username')
            artists.forEach(user => {
                if (user.username === curUsername) {
                    this.userOwnsArtwork = true
                }
            });
        },

        deleteArtwork(artworkId) {
            AXIOS.delete("/artworks/".concat(artworkId), {}, {
            })
                .then(this.deleted = true)
                .catch(e => {
                    var errorMsg = e.response.data.message;
                    console.log(errorMsg);
                    this.errorArtwork = errorMsg;
                });
        }
    }
};