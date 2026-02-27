package com.dian.prueba.repository

import com.dian.prueba.data.feed.mapper.ProductUIModel
import com.dian.prueba.data.featureFlag.model.FeatureFlagsResponse
import com.dian.prueba.data.nuFeed.model.NuFeedResponse
import com.dian.prueba.data.product.mapper.ProductDetailUIModel
import com.dian.prueba.network.LogoAPIService

/**
 * Repository pattern for clean separation
 */
interface FeedRepository {
    suspend fun fetchProductList(): List<ProductUIModel>
    suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse
    suspend fun getProductById(productId: String): ProductDetailUIModel?
    suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse
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
}