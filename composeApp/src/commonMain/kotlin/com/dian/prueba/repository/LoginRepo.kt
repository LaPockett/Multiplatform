package com.dian.prueba.repository

import com.dian.prueba.network.APIClient
import com.dian.prueba.utilities.Logger

class LoginRepo (
    private val apiClient: APIClient = APIClient(),
    private val logger: Logger = Logger()
){
    suspend fun login (id: String): String? {
        logger.warn("Haciendo login en LoginRepo...", "LoginRepo")
        return apiClient.requestLogin(id)
    }
}