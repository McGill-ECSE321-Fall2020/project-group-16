import axios from 'axios'
var config = require('../../config')

var frontendUrl
var backendUrl

if (this.NODE_ENV === "developmenta") {
    frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
    backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
} else {
    frontendUrl = 'https://' + config.build.host + ':' + config.build.port
    backendUrl = 'https://' + config.build.backendHost + ':' + config.build.backendPort
}

export const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: {
        'Access-Control-Allow-Origin': frontendUrl
    }
})