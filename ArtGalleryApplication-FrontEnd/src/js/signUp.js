import { AXIOS } from "./axiosInstance";
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
        signup: async function () {
            this.$emit("update:user", { username: "", isLoggedIn: false })
            var self = this;

            try {
                const res = await AXIOS.post(`/users/${this.username}?firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`)
                this.user = res.data
                this.$emit("update:user", { username: this.user.username, isLoggedIn: true });
            } catch (e) {
                console.log(e.response)
                if (
                    e.response) {
                    self.errorUser = e.response.data.message
                    this.$emit("update:error", self.errorUser)
                } else {
                    console.log(e)
                }

            }
        },
    },
};