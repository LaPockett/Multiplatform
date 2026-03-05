package com.dian.prueba.repository

import com.dian.prueba.data.featureFlag.model.FeatureFlagsResponse
import com.dian.prueba.network.service.FeatureFlagAPIService

interface FeatureFlagRepository {
    suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse
}

class FeatureFlagRepositoryImpl(
    private val featureFlagAPIService: FeatureFlagAPIService
) : FeatureFlagRepository {
    override suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse {
        return featureFlagAPIService.getFeatureFlags(userId)
    }
}