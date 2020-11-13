import { AXIOS } from "./axiosInstance";
import UserProfileInfo from "../components/UserProfileInfo";
import PastOrders from "../components/PastOrders"
export default {
    name: "ProfilePage",
    data() {
        return {
            username: "",
            user: "",

        };
    },
    components: {
        UserProfileInfo,
        PastOrders
    },
    created: function() {
        this.username = this.$route.params.username;
        console.log(this.username + "router")
        AXIOS.get(`/users/${this.username}`)
            .then((response) => {
                console.log(response.data)
                this.user = response.data;
            })
            .catch((e) => {
                console.log(e);
            });
    },
};