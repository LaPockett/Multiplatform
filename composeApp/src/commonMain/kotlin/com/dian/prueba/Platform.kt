package com.dian.prueba

import io.ktor.client.HttpClient

interface Platform {
    val name: String

    companion object {
        fun isAndroid(): Boolean {
            return isAndroid
        }
    }
}

expect fun getPlatform(): Platform
expect val isAndroid: Boolean

//expect fun createHttpClient(): HttpClient