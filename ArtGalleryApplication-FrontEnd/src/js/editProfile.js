import { AXIOS } from "./axiosInstance";

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

            errorNewFirstLastName: "",
            errorNewEmail: "",
            errorNewDescription: "",
            errorNewImageUrl: "",
            errorNewPassword: "",


            errorTargetUser: "",
            errorCurrentUser: "",

            response: []
        };
    },

    created: function () {
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

            AXIOS.put("users/" + this.theTargetUser.username + "/update-email/", {}, {
                params: {
                    newEmail: newEmail,
                },
            }).then((response) => {
                this.theTargetUser.push(response.data);

                //reinitialize the field
                this.newEmail = "";
            }).catch(function(err) {
                console.log(err.response);
                self.errorNewEmail = "Error: " + err.response.data.message;
            });
        },


        updateFirstLastName: function(newFirstName, newLastName) {
            var self = this;

            AXIOS.put("users/" + this.theTargetUser.username + "/update-name/", {}, {
                params: {
                    newFirstName: newFirstName,
                    newLastName: newLastName
                },
            }).then((response) => {
                this.theTargetUser.push(response.data);

                //reinitialize the field
                this.newFirstName = "";
                this.newLastName = "";
            }).catch(function(err) {
                console.log(err.response);
                self.errorNewFirstLastName = "Error: " + err.response.data.message;
            });
        },


        updatePassword: function(verificationPassword, newPassword) {
            var self = this;

            AXIOS.put("users/" + this.theTargetUser.username + "/update-password/", {}, {
                params: {
                    password: verificationPassword,
                    newPassword: newPassword
                },
            }).then((response) => {
                this.theTargetUser.push(response.data);

                //reinitialize the field
                this.verificationPassword = "";
                this.newPassword = "";
            }).catch(function(err) {
                console.log(err.response);
                self.errorNewPassword = "Error: " + err.response.data.message;
            });
        },


        updateDescription: function(newDescription) {
            var self = this;

            AXIOS.put("users/" + this.theTargetUser.username + "/update-description/", {}, {
                params: {
                    description: newDescription,
                },
            }).then((response) => {
                this.theTargetUser.push(response.data);

                //reinitialize the field
                this.newDescription = "";
            }).catch(function(err) {
                console.log(err.response);
                self.errorNewDescription = "Error: " + err.response.data.message;
            });
        },


        updateImage: function(newImageUrl) {
            var self = this;

            AXIOS.put("users/" + this.theTargetUser.username + "/update-profile-image-url/", {}, {
                params: {
                    imageUrl: newImageUrl,
                },
            }).then((response) => {
                this.theTargetUser.push(response.data);

                //reinitialize the field
                this.newImageUrl = "";
            }).catch(function(err) {
                console.log(err.response);
                self.errorNewImageUrl = "Error: " + err.response.data.message;
            });
        },
    },
}