// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import BootstrapVue from "bootstrap-vue"
import VueCardFormat from 'vue-credit-card-validation';
import firebase from 'firebase';
import App from './App'
import {
    router
} from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(VueCardFormat);
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    template: '<App/>',
    components: {
        App
    },

    created() {
        // Your web app's Firebase configuration
        // For Firebase JS SDK v7.20.0 and later, measurementId is optional
        var firebaseConfig = {
            apiKey: "AIzaSyAO3tUXMhoOWUpAw4Iwt25r6RTnreAnG1g",
            authDomain: "artgalleryapplication-16.firebaseapp.com",
            databaseURL: "https://artgalleryapplication-16.firebaseio.com",
            projectId: "artgalleryapplication-16",
            storageBucket: "artgalleryapplication-16.appspot.com",
            messagingSenderId: "700718161969",
            appId: "1:700718161969:web:f0cb90fc32fae5f84a3510",
            measurementId: "G-QBMV8VKM1V"
        };
        // Initialize Firebase
        firebase.initializeApp(firebaseConfig);
        firebase.analytics();
    }
})