package com.dian.prueba

import coil3.ImageLoader
import io.ktor.client.HttpClient

enum class PlatformType {
    ANDROID,
    IOS,
    DESKTOP, 
    WEB,
}

interface Platform {
    val name: String
}

expect fun getPlatformType(): PlatformType
// Other way to use Ktor in Android and iOS
//expect fun createHttpClient(): HttpClient
expect fun getImageLoader(): ImageLoader