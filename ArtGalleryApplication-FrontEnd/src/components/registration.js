import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

export const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: {
        'Access-Control-Allow-Origin': frontendUrl
    }
})

// export function signup(firstName, lastName, username, email, password) {
//     console.log(firstName, lastName, username, email, password);
//     AXIOS.post(
//             `/users/${username}?firstName=${firstName}&lastName=${lastName}&email=${email}&password=${password}`
//         )
//         .then(response => {
//             this.user = response.data;
//             console.log(this.user);
//         })
//         .catch(e => {
//             this.errorUser = e;
//             console.log(this.errorUser);
//         });
// }

// export function login(username, password) {
//     AXIOS.get(`/users/${username}?password=${password}`)
//         .then(response => {
//             this.user = response.data;
//             console.log(this.user);
//         })
//         .catch(e => {
//             this.errorUser = e;
//             console.log(this.errorUser);
//         });
// }