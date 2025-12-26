package com.dian.prueba.model

import kotlinx.serialization.Serializable

@Serializable
data class FeedResponse(
    val data: FeedData
)

@Serializable
data class FeedData(
    val feed: List<FeedItem>
)

@Serializable
data class FeedItem(
    val isPremium: Boolean,
    val isFavorite: Boolean,
    val asset: AssetResponse
)

@Serializable
data class AssetResponse(
    val type: AssetType,
    val variants: List<Variant>? = null
)

@Serializable
data class Variant(
    val url: String
)

@Serializable
enum class AssetType {
    IMAGE,
    VIDEO
}
@Serializable
data class ProductUIModel(
    val imageUrl: String,
    val assetType : AssetType
)