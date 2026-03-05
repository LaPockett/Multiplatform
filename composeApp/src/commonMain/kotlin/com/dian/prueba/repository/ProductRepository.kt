package com.dian.prueba.repository


import com.dian.prueba.domain.product.model.ProductDetailUIModel
import com.dian.prueba.network.service.ProductAPIService

interface ProductRepository {
    suspend fun getProductById(productId: String): ProductDetailUIModel?
}

class ProductRepositoryImpl(
    private val productAPIService: ProductAPIService
) : ProductRepository {
    override suspend fun getProductById(productId: String): ProductDetailUIModel? {
        return productAPIService.getProductById(productId)
    }
}