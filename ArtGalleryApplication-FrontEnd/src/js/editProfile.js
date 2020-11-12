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

            errorTargetUser: "",
            errorCurrentUser: "",

            response: []
        };
    },

    created: function () {
        //first thing: get the username of the requested page
        var url = window.location.hash;
        var username = url.substring(url.lastIndexOf('/') + 1);


        //get the target user
        AXIOS.get("/users/".concat(username))
            .then((response) => {
                this.theTargetUser = response.data;
            }).catch((e) => {
                this.errorTargetUser = e;
            });

        //ge the current user
        AXIOS.get("/users/".concat(localStorage.getItem('username')))
        .then((response) => {
            this.theCurrentUser = response.data;
        }).catch((e) => {
            this.errorCurrentUser = e;
        });
    },

    methods: {
    },
}