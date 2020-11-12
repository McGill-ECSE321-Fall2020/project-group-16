import axios from "axios";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
    "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
    name: "createaddress",
    data() {
        return {
            addresses: [],

            newAddress: {
                streetAddress: '',
                streetAddress2: '',
                postalCode: '',
                city: 'Montreal',
                province: 'QB',
                country: 'Canada'
            },

            errorAddress: '',
            response: []
        }
    },
    created: function() {
        // Initializing persons from backend
        AXIOS.get("/address")
            .then(response => {
                // JSON responses are automatically parsed.
                this.addresses = response.data;
            })
            .catch(e => {
                this.errorAddress = e;
            });
    },
    methods: {

        createAddress: function(username, artworkId) {
            var indexUser = this.users.map(x => x.username).indexOf(username)
            var indexArtwork = this.artworks.map(x => x.artworkId).indexOf(parseInt(artworkId))
            var user = this.users[indexUser]
            var artwork = this.artworks[indexArtwork]

            AXIOS.post("/orders/place-order/".concat(user.username), {}, {
                    params: {
                        artworkId: artwork.artworkId
                    }
                })
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.orders.push(response.data);
                    this.errorPlaceOrder = ''
                })
                .catch(e => {
                    var errorMsg = e.response.data.message;
                    console.log(errorMsg);
                    this.errorPlaceOrder = errorMsg;
                });
        },
        createAddress: function(streetAddress, streetAddress2, postalCode, city, province, country) {
            AXIOS.post("/address/", {}, {
                    params: {
                        streetAddress: streetAddress,
                        streetAddress2: streetAddress2,
                        postalCode: postalCode,
                        city: city,
                        province: province,
                        country: country,
                    }
                })
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.addresses.push(response.data);
                    this.errorAddress = "";
                    this.newAddress.streetAddress = "";
                    this.newAddress.streetAddress2 = "";
                    this.newAddress.postalCode = "";
                })
                .catch(e => {
                    var errorMsg = e.response.data.message;
                    console.log(errorMsg);
                    this.errorAddress = errorMsg;
                });
        }
    }
};