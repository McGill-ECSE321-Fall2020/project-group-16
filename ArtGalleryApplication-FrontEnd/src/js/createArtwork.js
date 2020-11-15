import { AXIOS } from './axiosInstance'

export default {
    name: "CreateArtwork",
    data() {
        return {
            //artworks: [],
            newArtwork: {
                artworkId: '',
                title: '',
                description: '',
                dimensions: '',
                medium: '',
                collection: '',
                creationDate: '',
                price: '',
                imageUrl: '',
                status: 'ForSale',
                artists: []
            },

            artistToAdd: "",

            allUsers: [],


            errorArtwork: '',
            errorAllUsers: '',
            errorAddArtist: '',

            response: [],

            theCurrentUser: {
                username: "",
                admin: "",
            },
        }
    },
    created: function() {

        //first thing: get the id of the page

        //get the current user to get admin status
        AXIOS.get("/users/".concat(localStorage.getItem('username')))
            .then((response) => {
                this.theCurrentUser = response.data;
            }).catch(function(err) {
                console.log(err.response);
                this.errorCurrentUser = "Error: " + err.response.data.message;
        });

        //get all users
        AXIOS.get("/users/")
        .then((response) => {
            this.allUsers = response.data;
        }).catch(function(err) {
            console.log(err.response);
            this.errorAllUsers = "Error: " + err.response.data.message;
        });


    },

    methods: {

        createArtwork: function(title, description, creationDate, medium, imageUrl, price, status, dimension, collection) {
            var self = this;

            console.log(
                this.theCurrentUser.username,
                this.newArtwork.title,
                this.newArtwork.description,
                this.newArtwork.dimensions,
                this.newArtwork.medium,
                this.newArtwork.collection,
                this.newArtwork.creationDate,
                this.newArtwork.price,
                this.newArtwork.imageUrl
            )

            AXIOS.post("/artworks/".concat(this.newArtwork.title), {}, {
                params: {
<<<<<<< HEAD
                    title: this.newArtwork.title,
=======
>>>>>>> c288340269d29554ef9809a42c45647ef2f95be6
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
                console.log(this.newArtwork)
                //this.artworks.push(response.data);
                this.newArtwork = response.data;
            })
            .catch(function(err) {
                console.log(err.response);
                self.errorArtwork = "Error: " + err.response.data.message;
            });
        },

        addArtist: function() {
            var self = this;

            AXIOS.put("/artworks/" + this.newArtwork.artworkId + "/add-artist/", {}, {
                params: {
                    username: this.artistToAdd
                }
            })
            .then(response => {
                this.newArtwork.artists.push(response.data.artists[0]);
                this.artistToAdd = '';

                // this.errorArtwork = ''
                // this.newArtwork.title = ''
                // this.newArtwork.description = ''
                // this.newArtwork.dimensions = ''
                // this.newArtwork.medium = ''
                // this.newArtwork.collection = ''
                // this.newArtwork.creationDate = ''
                // this.newArtwork.price = ''
                // this.newArtwork.imageUrl = ''
            })
            .catch(function(err) {
                console.log(err.response);
                self.errorAddArtist = "Error: " + err.response.data.message;
            });
        },

        reloadPage: function() {
            location.reload();
        }
    }
};