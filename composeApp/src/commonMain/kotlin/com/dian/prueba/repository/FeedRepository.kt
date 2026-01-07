package com.dian.prueba.repository

import com.dian.prueba.model.ProductUIModel
import com.dian.prueba.modelNuFeed.NuFeedResponse
import com.dian.prueba.network.LogoAPIService

/**
 * Repository pattern for clean separation
 */
interface FeedRepository {
    suspend fun fetchProductList(): List<ProductUIModel>
    suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse
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
}