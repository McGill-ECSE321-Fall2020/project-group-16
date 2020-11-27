import SignUpForm from "../components/SignUpForm";
import LoginForm from "../components/LoginForm";
export default {
    name: "LandingPage",
    components: {
        SignUpForm,
        LoginForm,
    },

    /**
     * declaration of the page's data
     */
    data() {
        return {
            form: "signup",
            username: "",
            isLoggedIn: false,
            error: ""
        };
    },
    methods: {

        /**
         * update the user loggedIn status
         * @param {*} args 
         */
        updateUser: function (args) {
            this.username = args.username;
            this.isLoggedIn = args.isLoggedIn;
            console.log(this.username + this.isLoggedIn)
        },

        /**
         * update the error to be displayed
         * @param {*} error 
         */
        updateError: function (error) {
            this.error = error;
        }
    },
    watch: {

        /**
         * verifies the loggedIn status
         */
        isLoggedIn: function () {
            this.$emit("update:status", {
                username: this.username,
                isLoggedIn: this.isLoggedIn,
            });
        },
    },

    /**
     * 
     * @param {*} to 
     * @param {*} from 
     * @param {*} next 
     */
    beforeRouteLeave(to, from, next) {
        if (localStorage.getItem("username") === null) {
            next(false)
        } else {
            next()
        }
    }
};