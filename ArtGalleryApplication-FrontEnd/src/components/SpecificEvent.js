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
            errorRequest: "",
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
            this.errorEvent = e; 
        });
    },

    methods: {
    },
}
