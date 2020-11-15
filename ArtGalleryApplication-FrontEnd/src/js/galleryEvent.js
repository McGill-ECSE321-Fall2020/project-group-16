import { AXIOS } from './axiosInstance'

export default {
    name: "galleryEvents",
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
            response: []
        };
    },

    created: function () {
        var self = this;

        AXIOS.get("/events")
            .then((response) => {
                this.events = response.data;
            }).catch(function (err) {
                console.log(err.response);
                self.errorEvent = "Error: " + err.response.data.message;
            });
    },

    methods: {

        createEvent: function (name, description, date, startTime, endTime) {
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

            }).catch(function (err) {
                console.log(err.response);
                self.errorRequest = "Error: " + err.response.data.message;
            });
        },
    },
}