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
        updateUser: function(args) {
            console.log("Update user in landing")
            this.username = args.username;
            this.isLoggedIn = args.isLoggedIn;
            console.log(this.username + this.isLoggedIn)
        },
        updateError: function(error) {
            this.error = error;
        }
    },
    watch: {
        isLoggedIn: function() {
            console.log("Emit in Landing" + this.isLoggedIn)
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