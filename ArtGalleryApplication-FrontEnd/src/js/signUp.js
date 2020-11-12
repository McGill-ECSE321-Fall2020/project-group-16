import { AXIOS } from "../js/axiosInstance";
export default {
    name: "SignUpForm",
    data() {
        return {
            username: "",
            password: "",
            firstName: "",
            lastName: "",
            email: "",
            user: "",
            errorUser: "",
        };
    },
    methods: {
        signup: function() {
            console.log(
                this.firstName,
                this.lastName,
                this.username,
                this.email,
                this.password
            );
            AXIOS.post(
                    `/users/${this.username}?firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`
                )
                .then((response) => {
                    this.user = response.data;
                    console.log("Sign up post: " + this.user);
                    this.$emit("update:user", this.user.username);
                })
                .catch((e) => {
                    if (e.response) {
                        console.log(e.response)
                    } else if (e.request) {
                        console.log(e.response)
                    } else {
                        console.log(e)
                    }
                });
        },
    },
};