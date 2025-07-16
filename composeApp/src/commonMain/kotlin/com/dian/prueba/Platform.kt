package com.dian.prueba

import io.ktor.client.HttpClient

enum class PlatformType {
    ANDROID,
    IOS,
}

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect fun getPlatformType(): PlatformType

//expect fun createHttpClient(): HttpClient
