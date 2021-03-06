import { AXIOS } from './axiosInstance'

export default {
    name: "galleryEvents",
    
    /**
     * declaration of the page's data
     */
    data() {
        return {
            events: [],
            newEvent: {
                name: "",
                description: "",
                imageUrl: "",
                date: "2020-12-08",
                startTime: "09:00",
                endTime: "11:00",
            },

            errorEvent: "",
            errorRequest: "",
            response: [],

            createModalActive: false
        };
    },

    /**
     * Initialization of the page on creation
     */
    created: function() {
        var self = this;

        /**
         * get all the events
         */
        AXIOS.get("/events")
            .then((response) => {
                this.events = response.data;
            }).catch(function(err) {
                self.errorEvent = "Error: " + err.response.data.message;
            });
    },

    methods: {

        /**
         * Create an Event method
         * @param {*} name 
         * @param {*} description 
         * @param {*} date 
         * @param {*} startTime 
         * @param {*} endTime 
         */
        createEvent: function(name, description, date, startTime, endTime) {
            var self = this;

            AXIOS.post("/events/".concat(name), {}, {
                params: {
                    description: description,
                    imageUrl: "NULL",
                    date: date,
                    startTime: startTime,
                    endTime: endTime
                },
            }).then((response) => {
                this.events.push(response.data);
                //reinitialize the fields
                this.errorRequest = "";
                this.newEvent.name = "";
                this.newEvent.description = "";
                this.newEvent.imageUrl = "";
                this.newEvent.date = "2020-12-08";
                this.newEvent.startTime = "09:00";
                this.newEvent.endTime = "11:00";
                this.createModalActive = false

            }).catch(function(err) {
                self.errorRequest = "Error: " + err.response.data.message;
            });
        },

        /**
         * Show Modal method
         */
        showModal: function() {
            this.createModalActive = true
        },

        /**
         * Show modal method
         */
        hideModal: function() {
            this.createModalActive = false
        }
    },
}