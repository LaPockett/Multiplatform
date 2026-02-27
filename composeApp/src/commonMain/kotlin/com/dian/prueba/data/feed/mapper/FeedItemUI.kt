package com.dian.prueba.data.feed.mapper

import com.dian.prueba.data.feed.model.AssetResponse
import com.dian.prueba.data.feed.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class FeedItemUI(
    val isPremium: Boolean,
    val isFavorite: Boolean,
    val asset: AssetResponse,
    val product: Product
)
