export default {
    name: "UserProfileInfo",
    props: ['user', 'currentUser'],
    methods: {
        checkDescription: function () {
            if (this.user.description === "") {
                return `${this.currentUser ? "You" : "They"} have not added a description yet.`
            } else {
                return this.user.description
            }
        },
        editProfile: function () {
            console.log(this.user)
            this.$router.push({
                name: "EditProfile",
                params: { username: this.user.username },
            })
        },
        logOut: function () {
            localStorage.removeItem("username")
            this.$router.push({ name: "LandingPage" })
        }
    },

};