import SignUpForm from "../components/SignUpForm";
import LoginForm from "../components/LoginForm";
export default {
    name: "LandingPage",
    components: {
        SignUpForm,
        LoginForm,
    },
    data() {
        return {
            form: "signup",
            username: "",
            isLoggedIn: false,
            error: ""
        };
    },
    methods: {
        updateUser: function(username) {
            console.log("Update user in landing")
            this.username = username;
            this.isLoggedIn = true;
        },
        updateError: function(error) {
            this.error = error;
        }
    },
    watch: {
        isLoggedIn: function() {
            console.log("Emit in Landing")
            this.$emit("update:status", {
                username: this.username,
                isLoggedIn: this.isLoggedIn,
            });
        },
    },
    beforeRouteLeave(to, from, next) {
        if (localStorage.getItem("username") === null) {
            next(false)
        } else {
            next()
        }
    }
};