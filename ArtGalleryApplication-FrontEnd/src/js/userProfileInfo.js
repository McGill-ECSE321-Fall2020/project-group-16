export default {
    name: "UserProfileInfo",
    props: ['user', 'currentUser'],
    methods: {

        /**
         * Apply correct word "you"/"they", to a user opening a user account
         */
        checkDescription: function () {
            if (this.user.description === "") {
                return `${this.currentUser ? "You" : "They"} have not added a description yet.`
            } else {
                return this.user.description
            }
        },
        /**
         * On "edit profile" button click, redirect to Edit Profile page
         */
        editProfile: function () {
            console.log(this.user)
            this.$router.push({
                name: "EditProfile",
                params: { username: this.user.username },
            })
        },

        /**
         * on "LogOut" button click, redirect to landing page + clear localstorage => no "current" user
         */
        logOut: function () {
            localStorage.removeItem("username")
            this.$router.push({ name: "LandingPage" })
        }
    },

};