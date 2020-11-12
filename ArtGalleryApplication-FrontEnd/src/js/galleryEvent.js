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
            errorRequest: "",
            response: []
        };
    },

    created: function() {
        AXIOS.get("/events")
            .then((response) => {
                this.events = response.data;
            }).catch((e) => {
                this.errorEvent = e;
            });
    },

    methods: {

        createEvent: function(name, description, imageUrl, date, startTime, endTime) {
            AXIOS.post("/events/".concat(name), {}, {
                params: {
                    description: description,
                    imageUrl: imageUrl,
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
                this.newEvent.date = "2017-12-08";
                this.newEvent.startTime = "09:00";
                this.newEvent.endTime = "11:00";

            }).catch((e) => {
                //todo: send back the illegalArgument Error from backend rather than the error code
                console.log(e);
                this.errorRequest = e;
            });
        },
    },
}