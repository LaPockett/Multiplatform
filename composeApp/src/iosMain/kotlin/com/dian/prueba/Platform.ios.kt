package com.dian.prueba

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import platform.UIKit.UIDevice
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
/*actual fun createHttpClient(): HttpClient = HttpClient(Darwin) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
    engine {
        configureRequest {
            setTimeoutInterval(15000.0)
        }
    }
}*/