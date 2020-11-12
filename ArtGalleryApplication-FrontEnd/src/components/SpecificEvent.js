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
            toRegisterUsername: "",
            errorNotEvent: "",
            errorRegister: "",
            response: []
        };
    },

    created: function () {
        //first thing: get the id of the page
        var url = window.location.hash;
        var id = url.substring(url.lastIndexOf('/') + 1);

        AXIOS.get("/events/".concat(id))
        .then((response) => { 
            this.theEvent = response.data;
        }).catch((e) => { 
            this.errorNotEvent = e; 
        });
    },

    methods: {

        registerToEvent: function (username) {
            AXIOS.put("/events/register/", {}, { 
                params: {
                    username: username,
                    eventId: this.theEvent.id
                },
            }).then((response) => {
                this.toRegisterUsername = "";
                this.errorRegister = "";
                location.reload();
                //todo: location.reload() considered bad, is there a better way?
            }).catch((e) => {
                //todo: send back the illegalArgument Error from backend rather than the error code
                console.log(e);
                this.errorRegister = e;
            });
        },

    },
}
