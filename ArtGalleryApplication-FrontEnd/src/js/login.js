import { AXIOS } from "../js/axiosInstance";
export default {
    name: "LoginForm",
    data() {
        return {
            username: "",
            password: "",
            user: "",
            errorUser: "",
        };
    },
    methods: {

        login: function() {
            var self = this;
            AXIOS.get(`/users/${this.username}?password=${this.password}`)
                .then((response) => {

                    this.user = response.data;
                    console.log("Login: " + this.user)
                    this.$emit("update:user", this.user.username);

                })
                .catch(function(e) {
                    self.errorUser = e;
                    console.log(self.errorUser);
                });
        },
    },
};