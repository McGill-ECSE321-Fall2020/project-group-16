import { AXIOS } from "./axiosInstance";

export default {
    name: "specificEvent",

    /**
     * declaration of the page's data
     */
    data() {
        return {
            theEvent: {
                id: "",
                name: "",
                eventDescription: "",
                eventImageUrl: "",
                eventDate: "",
                startTime: "",
                endTime: "",
                participants: [],
            },
            theCurrentUser: {
                username: "",
                admin: "",
            },

            isCurrUserRegistered: "",
            userToUnregister: "",

            errorNotEvent: "",
            errorRegister: "",
            errorCurrentUser: "",
            errorDeleteEvent: "",

            response: []
        };
    },

    created: function () {
        var self = this;
        
        //first thing: get the id of the page
        var url = window.location.hash;
        var id = url.substring(url.lastIndexOf('/') + 1);

        //get the current user to get admin status
        AXIOS.get("/users/".concat(localStorage.getItem('username')))
            .then((response) => {
                this.theCurrentUser = response.data;
            }).catch(function(err) {
                console.log(err.response);
                self.errorCurrentUser = "Error: " + err.response.data.message;
            });

        //get the event for setup
        AXIOS.get("/events/".concat(id))
            .then((response) => {
                this.theEvent = response.data;
            }).catch(function(err) {
                console.log(err.response);
                self.errorNotEvent = "Error: " + err.response.data.message;
            });
    },

    methods: {

        registerToEvent: function () {
            var self = this;

            AXIOS.put("/events/register/", {}, {
                params: {
                    username: localStorage.getItem('username'),
                    eventId: this.theEvent.id
                },
            }).then((response) => {
                //this.errorRegister = "";
                this.theEvent = response.data;
                this.isCurrUserRegistered = "true";
            }).catch(function(err) {
                console.log(err.response);
                self.errorRegister = "Error: " + err.response.data.message;
            });
        },

        //for staff only
        deleteEvent: function () {
            var self = this;

            //first thing: get the id of the page
            var url = window.location.hash;
            var id = url.substring(url.lastIndexOf('/') + 1);

            AXIOS.delete("/events/".concat(id))
                .then((response) => {
                    alert("The event was deleted!");
                    location.reload();
            }).catch(function(err) {
                console.log(err.response);
                self.errorNotEvent = "Error: " + err.response.data.message;
            });
        },

        //for admin only
        unregisterUser: function () {
            var self = this;

            AXIOS.put("/events/unregister/", {}, {
                params: {
                    username: this.userToUnregister,
                    eventId: this.theEvent.id
                },
            }).then((response) => {
                alert("user " + this.userToUnregister + " was unregistered!");
                this.errorRegister = "";
                this.theEvent = response.data;
                if (this.userToUnregister === this.theCurrentUser.username) this.isCurrUserRegistered = "";
            }).catch(function(err) {
                console.log(err.response);
                self.errorRegister = "Error: " + err.response.data.message;
            });
        },
    },
}