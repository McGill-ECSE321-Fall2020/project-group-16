import { AXIOS } from "./axiosInstance";
import UserProfileInfo from "../components/UserProfileInfo";
import PastOrders from "../components/PastOrders"
export default {
    name: "ProfilePage",
    data() {
        return {
            username: "",
            user: "",
            currentUser: false

        };
    },
    components: {
        UserProfileInfo,
        PastOrders
    },
    methods: {
        getUser(route, obj) {
            obj.username = route.params.username;
            console.log(localStorage.getItem("username"))
            console.log(obj.username)
            console.log(obj.username + "router")
            AXIOS.get(`/users/${obj.username}`)
                .then((response) => {
                    console.log(response.data)
                    obj.user = response.data;
                })
                .catch(function(e) {
                    console.log(e);
                });
        },
        setData(user, currentUser) {
            this.user = user,
                this.currentUser = currentUser
        }
    },
    beforeRouteEnter(to, from, next) {
        next(vm => {
            if (localStorage.getItem("username") === to.params.username) {
                vm.currentUser = true
                vm.getUser(to, vm)
                next()
            } else if (localStorage.getItem("username" === null)) {
                next('/')
            } else {
                vm.currentUser = false
                vm.getUser(to, vm)
                next()
            }
        })


    },
    beforeRouteUpdate(to, from, next) {
        this.user = null
        this.currentUser = false
        if (localStorage.getItem("username") === to.params.username) {
            this.currentUser = true
            this.getUser(to, this)
            next()
        } else if (localStorage.getItem("username" === null)) {

            next('/')
        } else {
            this.currentUser = false
            this.getUser(to, this)
            next()
        }

    }

};