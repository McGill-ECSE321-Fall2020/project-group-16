import axios from 'axios'
var config = require('../../config')

/**
 * Creation and configuration of the AXIOS instance for all the javascript files in this directory.
 */

var frontendUrl
var backendUrl

if (process.env.NODE_ENV === "development") {
    frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
    backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
} else {
    frontendUrl = 'https://' + config.build.host + ':' + config.build.port
    backendUrl = 'https://' + config.build.backendHost + ':' + config.build.backendPort
}

export const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: {
        'Access-Control-Allow-Origin': '*'
    },
    mode: 'cors'
})