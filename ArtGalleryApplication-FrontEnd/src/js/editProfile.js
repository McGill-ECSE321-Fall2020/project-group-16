import { AXIOS } from "./axiosInstance";
import firebase from 'firebase';

export default {
    name: "editProfile",
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
                console.log(err.response)
                self.errorTargetUser = err.response.data.message;
            });

        //get the current user
        AXIOS.get("/users/".concat(localStorage.getItem('username')))
            .then((response) => {
                this.theCurrentUser = response.data;
            }).catch(function(err) {
                console.log(err.response);
                self.errorCurrentUser = err.response.data.message;

            });
    },

    methods: {

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
                console.log(err.response);
                self.error = err.response.data.message;
                this.success = false

            });
        },


        updateFirstLastName: function(newFirstName, newLastName) {
            var self = this;

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
                console.log(err.response);
                self.error = err.response.data.message;
                this.success = false

            });
        },


        updatePassword: function(verificationPassword, newPassword) {
            var self = this;

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
                console.log(err.response);
                self.error = err.response.data.message;
                this.success = false

            });
        },


        updateDescription: function(newDescription) {
            var self = this;

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
                console.log(err.response);
                self.error = err.response.data.message;
                this.success = false

            });
        },


        updateImage: function(newImageUrl) {
            var self = this;

            AXIOS.put("/users/" + this.theTargetUser.username + "/update-profile-image-url/", {}, {
                params: {
                    imageUrl: newImageUrl,
                },
            }).then((response) => {
                this.theTargetUser = response.data;

                //reinitialize the field
                this.newImageUrl = "";
                this.error = "";
                console.log(this.theTargetUser.profileImageUrl)
            }).catch(function(err) {
                console.log(err.response);
                self.error = err.response.data.message;
            });
        },

        displayNameModal: function() {
            this.success = false
            this.error = ""

            this.hideDescriptionModal()
            this.hideEmailModal()
            this.hidePasswordModal()

            this.nameModalActive = true


        },

        displayEmailModal: function() {
            this.success = false
            this.error = ""


            this.hideDescriptionModal()
            this.hideNameModal()
            this.hidePasswordModal()

            this.emailModalActive = true

        },

        displayDescriptionModal: function() {
            this.success = false
            this.error = ""


            this.hideNameModal()
            this.hideEmailModal()
            this.hidePasswordModal()
            this.descriptionModalActive = true

        },

        displayPasswordModal: function() {
            this.success = false
            this.error = ""


            this.hideDescriptionModal()
            this.hideEmailModal()
            this.hideNameModal()
            this.passwordModalActive = true

        },

        hideNameModal: function() {
            this.nameModalActive = false
        },

        hideEmailModal: function() {
            this.emailModalActive = false
        },

        hideDescriptionModal: function() {
            this.descriptionModalActive = false
        },

        hidePasswordModal: function() {
            this.passwordModalActive = false
        },

        done: function() {
            this.$router.push({ name: "ProfilePage", params: { username: this.theTargetUser.username } })
        },


        create() {
            const post = {
                photo: this.img1,
                caption: this.caption
            }
            firebase.database().ref('PhotoGallery').push(post)
                .then((response) => {
                    console.log(response)
                    this.updateImage(this.img1)
                    this.imageData = null

                })
                .catch(err => {
                    console.log(err)
                })
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
                }, error => { console.log(error.message) },
                () => {
                    this.uploadValue = 100;
                    storageRef.snapshot.ref.getDownloadURL().then((url) => {
                        this.img1 = url;
                        console.log(this.img1)
                    });
                }
            );
        },
    },
}