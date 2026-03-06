package com.dian.prueba.repository

import com.dian.prueba.network.ApiService
import com.dian.prueba.utilities.Logger

//ref: https://medium.com/@appdevinsights/repository-design-pattern-in-kotlin-1d1aeff1ad40

interface LoginRepository{
    suspend fun login(id: Int): String?
}
class LoginRepositoryImpl (
    private val apiService: ApiService,
    private val logger: Logger = Logger("LoginRepo")
): LoginRepository{
    override suspend fun login (id: Int): String? {
        logger.warn("Haciendo login en LoginRepo...")
        return apiService.requestLogin(id.toString())
    }
}
