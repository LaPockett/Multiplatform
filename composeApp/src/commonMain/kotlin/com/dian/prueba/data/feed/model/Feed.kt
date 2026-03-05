package com.dian.prueba.data.feed.model

import com.dian.prueba.data.feed.enums.AssetMediaType
import com.dian.prueba.data.nuFeed.model.Variant
import kotlinx.serialization.Serializable

@Serializable
data class FeedResponse(
    val data: FeedData
)

@Serializable
data class FeedData(
    val feed: List<FeedItemResponse>
)

@Serializable
data class FeedItemResponse(
    val isPremium: Boolean,
    val isFavorite: Boolean,
    val asset: AssetResponse,
    val product: Product
)

@Serializable
data class AssetResponse(
    val url: String? = null,
    val type: AssetMediaType,
    val variants: List<Variant>? = emptyList(),
    val posterVariants: List<Variant>? = emptyList()
)