package com.dian.prueba.repository

import com.dian.prueba.utilities.Logger

object WebViewHeaderManager {

    private val headers: MutableMap<String, String> = mutableMapOf()
    private val logger = Logger("WebViewHeaderManager")

    fun updateLoginCookie(cookie: String) {
        headers["Cookie"] = "login_cookie=$cookie"
        logger.debug(headers.toString())
    }
    fun getHeaders(): Map<String, String> = headers

    fun updateAccessToken(it: String) {
        headers["Authorization"] = "BearerAccessToken $it"
        logger.debug(headers.toString())
    }

    fun updateRefreshToken(it : String) {
        headers["Refresh"] = "Bearer $it"
        logger.debug(headers.toString())
    }


}
