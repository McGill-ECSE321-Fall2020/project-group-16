import NavBar from '../components/NavBar'
export default {
    name: "app",
    components: { NavBar, },

    /**
     * declaration of the page's data
     */
    data() {
        return {
            username: "",
            isLoggedIn: "",
        };
    },
    methods: {

        /**
        * Method to update the loggedIn status of the user
        */
        updateStatus: function(status) {
            this.username = status.username;
            this.isLoggedIn = status.isLoggedIn;
        },

        /**
         * Method to login
         * ie, set the localstorage username to the status username
         * @param {*} status 
         */
        logIn: function(status) {
            if (status.isLoggedIn) {
                localStorage.setItem("username", status.username);
                this.$router.push({
                    name: "ProfilePage",
                    params: { username: status.username },
                });
            }
        },
    },
};