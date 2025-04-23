package com.dian.prueba.API

import com.dian.prueba.DataClass.Login
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class APIClient {
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

    suspend fun requestLogin(id: Int): String? {
        println("DIAN LOG - Iniciando login...")
        return try {
            val result: Login = client.get("https://jsonplaceholder.typicode.com/users/$id").body()
            println("DIAN LOG - Respuesta del login:$result")
            _loginToken = result.email
            println("DIAN LOG - Token guardado: $_loginToken")
            _loginToken
        } catch (e: Exception) {
            println("DIAN LOG ERROR - Fallo en login: ${e.stackTraceToString()}")
            null
        }
    }

}
