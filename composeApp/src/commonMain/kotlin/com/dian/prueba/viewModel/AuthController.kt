package com.dian.prueba.viewModel

import com.dian.prueba.data.tokens.model.Tokens
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.time.Instant

class AuthController(
    private val tokenStorage: TokenStorage,
    private val logger: Logger = Logger("AuthController")
) {
    private val dummyTokenUrl = "http://192.168.10.130:8160/auth/access_token_dummy"

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    //* Llama esto antes de cada request protegido
    suspend fun getValidToken(): String {
        val stored = tokenStorage.loadTokens()

        return if (stored == null || isExpired(stored)) {
            logger.warn("Token nulo o expirado → obteniendo nuevo token dummy :p")
            logger.debug("Token expirado, generando nuevo")
            fetchDummyToken()
        } else {
            logger.debug("Token válido, reutilizando!")
            stored.accessToken
        }
    }

    private fun isExpired(tokens: Tokens): Boolean {
        val expiration = tokens.expiration ?: return true
        return try {
            val expiresAt = Instant.parse(expiration)
            val now = Clock.System.now()
            val isExpired = now >= expiresAt
            if (isExpired) logger.warn("Token expirado a las $expiration, ahora son $now")
            isExpired
        } catch (e: Exception) {
            logger.error(e)
            true
        }
    }

    suspend fun forceRefresh(): String {
        logger.warn("Forzando refresh token")
        tokenStorage.clear()
        return fetchDummyToken()
    }
    private suspend fun fetchDummyToken(): String {
        val tokens = httpClient
            .get(dummyTokenUrl)
            .body<Tokens>()
        tokenStorage.saveTokens(tokens)
        logger.debug("Nuevo token obtenido ${tokens.accessToken}, expira: ${tokens.expiration}")
        return tokens.accessToken
    }
}