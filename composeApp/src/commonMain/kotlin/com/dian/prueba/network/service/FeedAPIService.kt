package com.dian.prueba.network.service

import com.dian.prueba.domain.feed.model.ProductUIModel

interface FeedAPIService {
    suspend fun getProductList(): List<ProductUIModel>
}