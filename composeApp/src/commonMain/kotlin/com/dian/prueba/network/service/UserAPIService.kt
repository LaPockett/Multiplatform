package com.dian.prueba.network.service

import com.dian.prueba.data.featureFlag.model.FeatureFlagsResponse

interface UserAPIService {
    suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse
    suspend fun getCurrentRoute(currentRoute: String?, userId: String): String
}