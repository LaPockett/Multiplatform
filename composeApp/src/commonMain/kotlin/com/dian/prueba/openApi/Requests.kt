package com.dian.prueba.openApi

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.serialization.kotlinx.json.json
import io.ktor.http.ContentType
import kotlinx.serialization.json.Json
import io.ktor.http.contentType

// With Dependency Injection

interface HttpClientProvider {
    suspend fun registerUser(payload: UserProfile): String
    suspend fun healthPing(): String
    suspend fun getUserProfile(payload: UserProfile): String
}

class APIClient : HttpClientProvider {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }
    override suspend fun registerUser(payload: UserProfile): String {
        return client.post("http://192.168.10.209:8000/v0.1/identity/register") {
            url {
                parameters.append("self", "true")
                parameters.append("logger", "true")
            }
            contentType(ContentType.Application.Json)
            setBody(payload)

        }.body()
    }

    override suspend fun healthPing(): String {
        return client.get("http://192.168.10.209:8000/v0.1/health/ping"){
            url {
                parameters.append("self", "true")
            }
        }.body()
    }

    override suspend fun getUserProfile(payload: UserProfile): String {
        return client.get("http://192.168.10.209:8000/v0.1/identity/profile") {
            url {
                parameters.append("self", "true")
                parameters.append("authorization", "true")
            }
            contentType(ContentType.Application.Json)
            setBody(payload)
        }.body()
    }
}

class Requests(private val httpClientProvider: HttpClientProvider) {

    suspend fun registerUser(payload: UserProfile) : String{
        println("** Register user **")
        return try {
            val response: String = httpClientProvider.registerUser(payload)
            response
        } catch (e: Exception) {
            "Exception: $e"
        }
    }

    suspend fun healthPing(): String {
        println("** Health Ping - Pong **")
        return try {
            val response: String = httpClientProvider.healthPing()
            response
        } catch (e: Exception) {
            "Exception: $e"
        }
    }

    suspend fun getUserProfile(payload: UserProfile): String {
        println("** Get user Profile **")
        return try {
            val response: String = httpClientProvider.getUserProfile(payload)
            response
        } catch (e: Exception) {
            "Exception: $e"
        }
    }
}
/*
// Without Dependency Injection
val client = HttpClient() {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        })
    }
}

suspend fun registerUser() {
    val payload = UserProfile(username = "string", email = "string")
    val jsonString = Json.encodeToString(UserProfile.serializer(), payload)
    println("** Register User **")
    println("Payload: ${Json.encodeToString(UserProfile.serializer(), payload)}")
    println(payload)

    try {
        val response: String =
            client.post("http://192.168.10.209:8000/v0.1/identity/register") {
                url {
                    parameters.append("self", "true")
                    parameters.append("logger", "true")
                }
                contentType(ContentType.Application.Json)
                setBody(payload)
            }.bodyAsText()
        println("API Respuesta: $response")
        //println("API Usuario: ${response.username}, Email: ${response.email}")
    } catch (e: Exception) {
        println("API Error al registrar: ${e.message}")
    }
    println("** End Register User **")
}*/

suspend fun main() {
    println("Prueba de API")
    //registerUser() //Without dependency injection
    val requests = Requests(APIClient())
    val payload = UserProfile(username = "string", email = "string")
    println(requests.healthPing())
    println(requests.getUserProfile(payload))
    println(requests.registerUser(payload))
}