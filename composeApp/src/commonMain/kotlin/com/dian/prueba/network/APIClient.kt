package com.dian.prueba.network

import com.dian.prueba.model.Login
import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.UpdateStorage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class APIClient {
    private val logger = Logger()

    private var _loginToken: String? = null
    val loginToken: String? get() {
        return _loginToken
    }

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun requestLogin(id: String): String? {
        logger.warn("Iniciando login...", "requestLogin")

        return try {
            val result: Login = client.get("https://jsonplaceholder.typicode.com/users/$id").body()
            logger.debug(result.toString(), "JSON Response")
            _loginToken = result.email
            logger.debug(_loginToken.toString(), "Login Token")
            _loginToken
        } catch (e: Exception) {
            logger.error(e, "requestLoginException")
            null
        }
    }

    fun checkUpdateAvailable(): UpdateInfo {
        logger.warn("Checking for updates...", "checkUpdateAvailable")
        UpdateStorage.loadUpdateInfo()?.let { savedInfo ->
            if (savedInfo.currentVersion == savedInfo.newVersion){
                return savedInfo
            }
        }
        return UpdateInfo(
            mustUpdate = true,
            currentVersion = "1.2",
            newVersion = "1.3", // Desde aquí podemos cambiar a otra versión
        )
    }
    /*fun updateApp() : UpdateInfo {
        logger.warn("Updating app...", "updateApp")
        return UpdateStorage.updateToNewVersion("1.3")
    }*/
}
