import NavBar from '../components/NavBar'
export default {
    name: "app",
    components: { NavBar, },
    data() {
        return {
            username: "",
            isLoggedIn: "",
        };
    },
    methods: {
        updateStatus: function(status) {
            console.log("Emit worked");
            this.username = status.username;
            this.isLoggedIn = status.isLoggedIn;
        },
        logIn: function(status) {
            console.log("logIn worked")
            if (status.isLoggedIn) {
                console.log("Updated");
                localStorage.setItem("username", status.username);
                console.log(localStorage.getItem("username"))
                this.$router.push({
                    name: "ProfilePage",
                    params: { username: status.username },
                });
            }
        },
    },
    // watch: {
    //     isLoggedIn: function() {
    //         console.log("Watch worked")
    //         if (this.isLoggedIn) {
    //             console.log("Updated");
    //             localStorage.setItem("username", this.username);
    //             console.log(localStorage.getItem("username"))
    //             this.$router.push({
    //                 name: "ProfilePage",
    //                 params: { username: this.username },
    //             });
    //         }
    //     },
    // },
};