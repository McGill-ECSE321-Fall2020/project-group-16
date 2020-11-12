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
            AXIOS.get(`/users/${this.username}?password=${this.password}`)
                .then((response) => {

                    this.user = response.data;
                    console.log("Login: " + this.user)
                    this.$emit("update:user", this.user.username);

                })
                .catch((e) => {
                    this.errorUser = e;
                    console.log(this.errorUser);
                });
        },
    },
};