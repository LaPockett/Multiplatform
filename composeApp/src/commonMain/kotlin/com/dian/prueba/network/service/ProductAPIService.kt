package com.dian.prueba.network.service

import com.dian.prueba.domain.product.model.ProductDetailUIModel

interface ProductAPIService {
    suspend fun getProductById(productId: String): ProductDetailUIModel?
}