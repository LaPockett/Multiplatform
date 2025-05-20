package com.dian.prueba.repository


import com.dian.prueba.network.APIClient
import com.dian.prueba.utilities.Logger

//https://medium.com/@appdevinsights/repository-design-pattern-in-kotlin-1d1aeff1ad40
interface LoginRepository{
    suspend fun login(id: Int): String?
}
class LoginRepositoryImpl (
    private val apiClient: APIClient = APIClient(),
    private val logger: Logger = Logger()
): LoginRepository{
    override suspend fun login (id: Int): String? {
        logger.warn("Haciendo login en LoginRepo...", "LoginRepo")
        return apiClient.requestLogin(id.toString())
    }
}
