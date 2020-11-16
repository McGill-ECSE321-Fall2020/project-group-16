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

        login: async function () {
            this.$emit("update:user", { username: "", isLoggedIn: false })
            var self = this;
            try {
                const response = await AXIOS.get(`/users/${this.username}?password=${this.password}`)
                if (this.password != response.data.password) {
                    this.errorUser = "Incorrect password."
                    this.$emit("update:error", this.errorUser)
                } else {
                    this.user = response.data
                    this.$emit("update:user", { username: this.user.username, isLoggedIn: true });
                }

            } catch (e) {
                console.log(e.response.data)
                self.errorUser = e.response.data.message;
                console.log(self.errorUser);
                this.$emit("update:error", self.errorUser)
            };
        },
    },
};