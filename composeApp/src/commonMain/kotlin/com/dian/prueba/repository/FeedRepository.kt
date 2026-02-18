package com.dian.prueba.repository

import com.dian.prueba.model.ProductUIModel
import com.dian.prueba.modelFeatureFlag.FeatureFlagsResponse
import com.dian.prueba.modelNuFeed.NuFeedResponse
import com.dian.prueba.modelProduct.ProductDetailUIModel
import com.dian.prueba.network.LogoAPIService

/**
 * Repository pattern for clean separation
 */
interface FeedRepository {
    suspend fun fetchProductList(): List<ProductUIModel>
    suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse
    suspend fun getProductById(productId: String): ProductDetailUIModel?
    suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse
    suspend fun setFeatureFlag(userId: String, flagName: String, enabled: Boolean): FeatureFlagsResponse
}

class FeedRepositoryImpl(
    private val logoAPIService: LogoAPIService
): FeedRepository {
    override suspend fun fetchProductList(): List<ProductUIModel> {
        return logoAPIService.getProductList()
    }
    override suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse {
        return logoAPIService.getNuFeed(paginationIndex)
    }
    override suspend fun getProductById(productId: String): ProductDetailUIModel? {
        return logoAPIService.getProductById(productId)
    }

    override suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse {
        return logoAPIService.getFeatureFlags(userId)
    }

    override suspend fun setFeatureFlag(userId: String, flagName: String, enabled: Boolean): FeatureFlagsResponse {
        return logoAPIService.setFeatureFlag(userId, flagName, enabled)
    }
}