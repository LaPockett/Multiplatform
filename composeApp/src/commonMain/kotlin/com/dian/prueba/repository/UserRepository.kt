package com.dian.prueba.repository

import com.dian.prueba.data.featureFlag.model.FeatureFlagsResponse
import com.dian.prueba.network.service.UserAPIService

interface UserRepository {
    suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse
    suspend fun getCurrentRoute(currentRoute: String?, userId: String): String
}

class UserRepositoryImpl(
    private val userAPIService: UserAPIService
) : UserRepository {
    override suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse {
        return userAPIService.getFeatureFlags(userId)
    }

    override suspend fun getCurrentRoute(currentRoute: String?, userId: String): String {
        return userAPIService.getCurrentRoute(currentRoute, userId)
    }

}