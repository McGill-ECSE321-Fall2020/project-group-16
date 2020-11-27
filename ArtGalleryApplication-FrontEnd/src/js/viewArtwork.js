import { AXIOS } from "./axiosInstance.js";

export default {
    name: "ViewArtwork",

    /**
     * declaration of the page's data
     */
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

        /**
         * get the specific artwork of the page
         */
        AXIOS.get(`/artworks/${this.artwork.artworkId}`)
            .then(response => {
                this.artwork = response.data;
                this.isUserOwnerOfArtwork(response.data.artists)
            })
            .catch(e => {
                console.log(e);
            });

        /**
         * get the user that is using the platform
         */
        AXIOS.get("/users/".concat(localStorage.getItem('username')))
            .then((response) => {
                this.theCurrentUser = response.data;
            }).catch(function (err) {
                console.log(err.response);
                this.errorCurrentUser = "Error: " + err.response.data.message;
            });
    },
    methods: {

        /**
         * returns true if the user using the platform is artist of the artwork
         * @param {*} artists 
         */
        isUserOwnerOfArtwork(artists) {
            const curUsername = localStorage.getItem('username')
            artists.forEach(user => {
                if (user.username === curUsername) {
                    this.userOwnsArtwork = true
                }
            });
        },

        /**
         * allows the user (if artist of the artwork), to delete the artwork
         * @param {*} artworkId 
         */
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