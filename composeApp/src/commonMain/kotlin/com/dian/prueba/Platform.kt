package com.dian.prueba

import io.ktor.client.HttpClient

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

//expect fun createHttpClient(): HttpClient