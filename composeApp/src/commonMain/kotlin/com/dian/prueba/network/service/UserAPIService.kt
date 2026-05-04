package com.dian.prueba.network.service

import com.dian.prueba.data.featureFlag.model.FeatureFlagsResponse
import com.dian.prueba.data.tokens.model.Tokens

interface UserAPIService {
    suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse
    suspend fun getCurrentRoute(currentRoute: String?, userId: String): String
    suspend fun login(user: String, password: String): Tokens
}