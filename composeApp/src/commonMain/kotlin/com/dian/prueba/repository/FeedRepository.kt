package com.dian.prueba.repository

import com.dian.prueba.domain.feed.model.ProductUIModel
import com.dian.prueba.network.service.FeedAPIService

/**
 * Repository pattern for clean separation
 */
interface FeedRepository {
    suspend fun fetchProductList(): List<ProductUIModel>
}

class FeedRepositoryImpl(
    private val feedAPIService: FeedAPIService
) : FeedRepository {
    override suspend fun fetchProductList(): List<ProductUIModel> {
        return feedAPIService.getProductList()
    }
}