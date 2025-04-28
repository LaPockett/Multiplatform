package com.dian.prueba.network

import com.dian.prueba.model.Login
import com.dian.prueba.utilities.Logger
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

}
