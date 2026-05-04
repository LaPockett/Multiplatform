package com.dian.prueba.repository

import com.dian.prueba.data.tokens.model.Tokens
import com.dian.prueba.network.service.UserAPIService
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.TokenStorage

//ref: https://medium.com/@appdevinsights/repository-design-pattern-in-kotlin-1d1aeff1ad40

interface LoginRepository{
    suspend fun login(user: String, password: String): Tokens
}

class LoginRepositoryImpl(
    private val userApiService: UserAPIService,
    private val logger: Logger = Logger("LoginRepo"),
    private val tokenStorage: TokenStorage
) : LoginRepository {

    override suspend fun login(user: String, password: String): Tokens {
        val tokens = userApiService.login(user, password)
        tokenStorage.saveTokens(tokens)
        logger.debug("Tokens guardados tras login: $tokens")
        return tokens
    }
}