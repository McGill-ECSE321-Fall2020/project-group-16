import { AXIOS } from './axiosInstance'
import firebase from 'firebase';

export default {
    name: "CreateArtwork",
    data() {
        return {
            newArtwork: {
                artworkId: '',
                title: '',
                description: '',
                dimensions: '',
                medium: '',
                collection: '',
                creationDate: '2020-12-08',
                price: '',
                imageUrl: '',
                status: '',
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

            img1: '../assets/no-img.png',
            imageData: null,

            artworkCreated: ""
        }
    },
    created: function() {
        var self = this

        //first thing: get the id of the page

        //get the current user to get admin status
        AXIOS.get("/users/".concat(localStorage.getItem('username')))
            .then((response) => {
                this.theCurrentUser = response.data;
            }).catch(function(err) {
                this.errorCurrentUser = "Error: " + err.response.data.message;
            });

        //get all users
        AXIOS.get("/users/")
            .then((response) => {
                this.allUsers = response.data;
            }).catch(function(err) {
                self.errorAllUsers = "Error: " + err.response.data.message;
            });


    },

    methods: {

        createArtwork: function(title, description, creationDate, medium, imageUrl, price, status, dimension, collection) {
            var self = this;

            AXIOS.post("/artworks/".concat(this.newArtwork.title), {}, {
                    params: {

                        title: this.newArtwork.title,
                        description: this.newArtwork.description,
                        creationDate: this.newArtwork.creationDate,
                        medium: this.newArtwork.medium,
                        imageUrl: this.newArtwork.imageUrl,
                        price: this.newArtwork.price,
                        status: "ForSale",
                        dimensions: this.newArtwork.dimensions,
                        collection: this.newArtwork.collection,
                    }
                })
                .then(response => {
                    this.newArtwork.artworkId = response.data.artworkId;
                    this.newArtwork.title = response.data.title
                    this.newArtwork.description = response.data.description
                    this.newArtwork.dimensions = response.data.dimensions
                    this.newArtwork.medium = response.data.medium
                    this.newArtwork.collection = response.data.collection
                    this.newArtwork.creationDate = response.data.creationDate
                    this.newArtwork.price = response.data.price
                    this.newArtwork.imageUrl = response.data.imageUrl

                    this.addArtist(response.data.artworkId, this.theCurrentUser.username)
                        //indicate that the artwork was indeed created
                    this.artworkCreated = "true"

                })
                .catch(function(err) {
                    self.errorArtwork = "Error: " + err.response.data.message;
                });
        },

        addArtist: function(artworkId, username) {
            var self = this;

            AXIOS.put("/artworks/" + artworkId + "/add-artist/", {}, {
                    params: {
                        username: username
                    }
                })
                .then(response => {
                    this.$router.push('/browseart')
                })
                .catch(function(err) {
                    self.errorAddArtist = "Error: " + err.response.data.message;
                })
        },

        create() {

            const post = {
                photo: this.img1,
                caption: this.newArtwork.title
            }
            firebase.database().ref('PhotoGallery').push(post)
                .then((response) => {
                    this.imageData = null
                })
                .catch(err => {})
        },
        click1() {
            this.$refs.input1.click()
        },
        previewImage(event) {
            this.uploadValue = 0;
            this.img1 = null;
            this.imageData = event.target.files[0];
            this.onUpload()
        },
        onUpload() {
            this.img1 = null;
            const storageRef = firebase.storage().ref(`${this.imageData.name}`).put(this.imageData);
            storageRef.on(`state_changed`, snapshot => {
                    this.uploadValue = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
                }, error => {},
                () => {
                    this.uploadValue = 100;
                    storageRef.snapshot.ref.getDownloadURL().then((url) => {
                        this.img1 = url;
                        this.newArtwork.imageUrl = url
                    });
                }
            );
        },


    }


}