package com.dian.prueba.HeaderManager

object WebViewHeaderManager {

    private val headers: MutableMap<String, String> = mutableMapOf()

    fun updateLoginCookie(cookie: String) {
        headers["Cookie"] = "login_cookie=$cookie"
        println("dian log - Cookie guardada: login_cookie=$cookie")
    }
    fun getHeaders(): Map<String, String> = headers
}
