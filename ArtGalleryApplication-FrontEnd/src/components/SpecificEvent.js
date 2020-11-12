import axios from 'axios'
var config = require('../../config')

var backendConfigurer = function(){
    switch(process.env.NODE_ENV){
        case 'development':
            return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
            //return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
        case 'production':
            return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
    }
};

var backendUrl = backendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl,
  //headers: { 'Access-Control-Allow-Origin': frontendUrl }
})



export default {
    name: "specificEvent",
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
            
            errorNotEvent: "",
            errorRegister: "",
            errorCurrentUser: "",
            errorDeleteEvent: "",

            response: []
        };
    },

    created: function () {
        //first thing: get the id of the page
        var url = window.location.hash;
        var id = url.substring(url.lastIndexOf('/') + 1);

        //get the current user to get admin status
        AXIOS.get("/users/".concat(localStorage.getItem('username')))
        .then((response) => { 
            this.theCurrentUser = response.data;
        }).catch((e) => { 
            this.errorCurrentUser = e;
        });

        //get the event for setup
        AXIOS.get("/events/".concat(id))
        .then((response) => { 
            this.theEvent = response.data;
        }).catch((e) => { 
            this.errorNotEvent = e; 
        });
    },

    methods: {

        registerToEvent: function () {
            AXIOS.put("/events/register/", {}, { 
                params: {
                    username: localStorage.getItem('username'),
                    eventId: this.theEvent.id
                },
            }).then((response) => {
                this.errorRegister = "";
                location.reload();
                //todo: location.reload() considered bad, is there a better way?
            }).catch((e) => {
                //todo: send back the illegalArgument Error from backend rather than the error code
                console.log(e);
                this.errorRegister = e;
            });
        },


        //for staff only
        deleteEvent: function () {
            //first thing: get the id of the page
            var url = window.location.hash;
            var id = url.substring(url.lastIndexOf('/') + 1);

            AXIOS.delete("/events/".concat(id))
            .then((response) => { 
                alert("The event was deleted");
                location.reload();
            }).catch((e) => { 
                this.errorNotEvent = e; 
            });
        },
    },
}