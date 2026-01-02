package com.dian.prueba.repository

import com.dian.prueba.model.ProductUIModel
import com.dian.prueba.network.LogoAPIService

/**
 * Repository pattern for clean separation
 */
interface FeedRepository {
    suspend fun fetchProductList(): List<ProductUIModel>
}

class FeedRepositoryImpl(
    private val logoAPIService: LogoAPIService
): FeedRepository {
    override suspend fun fetchProductList(): List<ProductUIModel> {
        return logoAPIService.getProductList()
    }
}