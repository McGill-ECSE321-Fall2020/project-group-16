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
            error: "",
        };
    },
    methods: {
        signup: function() {
            var self = this;
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
                .catch(function(err) {
                    console.log(err.response);
                    self.error = err.response.data.message;
                    self.$emit("update:error", self.error)
                });
        },
    },
};