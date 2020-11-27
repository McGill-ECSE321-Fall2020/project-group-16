import { AXIOS } from "./axiosInstance";
import firebase from 'firebase';

export default {
    name: "editProfile",

    /**
     * declaration of the page's data
     */
    data() {
        return {
            theTargetUser: {
                username: "",
                password: "",
                email: "",
                firstName: "",
                lastName: "",
                description: "",
                profileImageUrl: "",
                address: "",
                admin: "",
            },
            theCurrentUser: {
                username: "",
                admin: "",
            },

            newEmail: "",
            newDescription: "",
            newImageUrl: "",
            newPassword: "",
            verificationPassword: "",
            newFirstName: "",
            newLastName: "",

            error: "",


            errorTargetUser: "",
            errorCurrentUser: "",

            response: [],

            nameModalActive: false,
            emailModalActive: false,
            descriptionModalActive: false,
            passwordModalActive: false,

            success: false,

            caption: '',
            img1: '',
            imageData: null
        };

    },

    /**
     * Initialization of the fields on page creation, using http requests to backend
     */
    created: function() {
        var self = this;

        //first thing: get the username of the requested page
        var url = window.location.hash;
        var username = url.substring(url.lastIndexOf('/') + 1);


        //get the target user
        AXIOS.get("/users/".concat(username))
            .then((response) => {
                this.theTargetUser = response.data;
            }).catch(function(err) {
                self.errorTargetUser = err.response.data.message;
            });

        //get the current user
        AXIOS.get("/users/".concat(localStorage.getItem('username')))
            .then((response) => {
                this.theCurrentUser = response.data;
            }).catch(function(err) {
                self.errorCurrentUser = err.response.data.message;
            });
    },

    methods: {

        /**
         * update the email method
         * @param {*} newEmail 
         */
        updateEmail: function(newEmail) {
            var self = this;

            AXIOS.put("/users/" + this.theTargetUser.username + "/update-email/", {}, {
                params: {
                    newEmail: newEmail,
                },
            }).then((response) => {
                this.theTargetUser = response.data;

                //reinitialize the field
                this.newEmail = "";
                this.error = "";
                this.hideEmailModal()
                this.success = true
            }).catch(function(err) {
                self.error = err.response.data.message;
                this.success = false

            });
        },


        /**
         * update the first and last names method
         * @param {*} newFirstName 
         * @param {*} newLastName 
         */
        updateFirstLastName: function(newFirstName, newLastName) {
            var self = this;

            /**
             * http request to the backend to update the names
             */
            AXIOS.put("/users/" + this.theTargetUser.username + "/update-name/", {}, {
                params: {
                    newFirstName: newFirstName,
                    newLastName: newLastName
                },
            }).then((response) => {
                this.theTargetUser = response.data;

                //reinitialize the field
                this.newFirstName = "";
                this.newLastName = "";
                this.error = "";
                this.hideNameModal()
                this.success = true

            }).catch(function(err) {
                self.error = err.response.data.message;
                this.success = false

            });
        },


        /**
         * update the password
         * @param {*} verificationPassword 
         * @param {*} newPassword 
         */
        updatePassword: function(verificationPassword, newPassword) {
            var self = this;

            /**
             * http request to update the password
             */
            AXIOS.put("/users/" + this.theTargetUser.username + "/update-password/", {}, {
                params: {
                    password: verificationPassword,
                    newPassword: newPassword
                },
            }).then((response) => {
                this.theTargetUser = response.data;

                //reinitialize the field
                this.verificationPassword = "";
                this.newPassword = "";
                this.error = "";
                this.hidePasswordModal()
                this.success = true
            }).catch(function(err) {
                self.error = err.response.data.message;
                this.success = false

            });
        },


        /**
         * update description of the user method
         * @param {*} newDescription 
         */
        updateDescription: function(newDescription) {
            var self = this;

            /**
             * http put request to the backend to update the description of the user
             */
            AXIOS.put("/users/" + this.theTargetUser.username + "/update-description/", {}, {
                params: {
                    description: newDescription,
                },
            }).then((response) => {
                this.theTargetUser = response.data;

                //reinitialize the field
                this.newDescription = "";
                this.error = "";
                this.hideDescriptionModal()
                this.success = true

            }).catch(function(err) {
                self.error = err.response.data.message;
                this.success = false

            });
        },


        /**
         * method to update the profile picture of the user
         * @param {*} newImageUrl 
         */
        updateImage: function(newImageUrl) {
            var self = this;

            /**
             * http request to the backend 
             */
            AXIOS.put("/users/" + this.theTargetUser.username + "/update-profile-image-url/", {}, {
                params: {
                    imageUrl: newImageUrl,
                },
            }).then((response) => {
                this.theTargetUser = response.data;

                //reinitialize the field
                this.newImageUrl = "";
                this.error = "";
            }).catch(function(err) {
                self.error = err.response.data.message;
            });
        },

        /**
         * method to display name modal
         */
        displayNameModal: function() {
            this.success = false
            this.error = ""

            this.hideDescriptionModal()
            this.hideEmailModal()
            this.hidePasswordModal()

            this.nameModalActive = true


        },

        /**
         * method to display the email modal
         */
        displayEmailModal: function() {
            this.success = false
            this.error = ""


            this.hideDescriptionModal()
            this.hideNameModal()
            this.hidePasswordModal()

            this.emailModalActive = true

        },

        /**
         * method to display the description modal
         */
        displayDescriptionModal: function() {
            this.success = false
            this.error = ""


            this.hideNameModal()
            this.hideEmailModal()
            this.hidePasswordModal()
            this.descriptionModalActive = true

        },

        /**
         * method to display the password modal
         */
        displayPasswordModal: function() {
            this.success = false
            this.error = ""


            this.hideDescriptionModal()
            this.hideEmailModal()
            this.hideNameModal()
            this.passwordModalActive = true

        },

        /**
         * method to hide the name modal
         */
        hideNameModal: function() {
            this.nameModalActive = false
        },
        
        /**
         * method to hide the email modal
         */
        hideEmailModal: function() {
            this.emailModalActive = false
        },

        /**
         * method to hide the description modal
         */
        hideDescriptionModal: function() {
            this.descriptionModalActive = false
        },

        /**
         * method to hide the password modal
         */
        hidePasswordModal: function() {
            this.passwordModalActive = false
        },

        done: function() {
            this.$router.push({ name: "ProfilePage", params: { username: this.theTargetUser.username } })
        },


        /**
         * creation of the image
         */
        create() {
            const post = {
                photo: this.img1,
                caption: this.caption
            }
            firebase.database().ref('PhotoGallery').push(post)
                .then((response) => {
                    this.updateImage(this.img1)
                    this.imageData = null

                })
                .catch(err => {
                })
        },
        click1() {
            this.$refs.input1.click()
        },

        /**
         * preview image method
         * @param {*} event 
         */
        previewImage(event) {
            this.uploadValue = 0;
            this.img1 = null;
            this.imageData = event.target.files[0];
            this.onUpload()
        },

        /**
         * upload image to the firebase bucket 
         */
        onUpload() {
            this.img1 = null;
            const storageRef = firebase.storage().ref(`${this.imageData.name}`).put(this.imageData);
            storageRef.on(`state_changed`, snapshot => {
                    this.uploadValue = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
                }, error => {  },
                () => {
                    this.uploadValue = 100;
                    storageRef.snapshot.ref.getDownloadURL().then((url) => {
                        this.img1 = url;
                    });
                }
            );
        },
    },
}