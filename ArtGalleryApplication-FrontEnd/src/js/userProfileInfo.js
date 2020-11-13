export default {
    name: "UserProfileInfo",
    props: {
        user: Object
    },
    methods: {
        checkDescription: function() {
            if (this.user.description === "") {
                return "You have not added a description yet."
            } else {
                return this.user.description
            }
        }
    }
};