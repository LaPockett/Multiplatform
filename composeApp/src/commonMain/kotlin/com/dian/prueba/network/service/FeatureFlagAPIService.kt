package com.dian.prueba.network.service

import com.dian.prueba.data.featureFlag.model.FeatureFlagsResponse

interface FeatureFlagAPIService {
    suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse
}