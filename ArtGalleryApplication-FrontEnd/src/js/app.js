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
            this.username = status.username;
            this.isLoggedIn = status.isLoggedIn;
        },
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