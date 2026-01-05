package com.dian.prueba.model

import kotlinx.serialization.Serializable

@Serializable
data class FeedResponse(
    val data: FeedData
)

@Serializable
data class Asset(
    val url: String? = null
)

@Serializable
data class FeedData(
    val feed: List<FeedItemUI>
)

@Serializable
data class FeedItemUI(
    val isPremium: Boolean,
    val isFavorite: Boolean,
    val asset: AssetResponse,
    val product: Product
)

@Serializable
data class AssetResponse(
    val url: String? = null, // En caso de que sea un video tendrá la URL en asset directamente
    val type: AssetType,
    val variants: List<Variant>? = null,
    val posterVariants: List<Variant>? = null
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
    val urlVideo : String? = null,
    val imageUrl: String,
    val posterVariants: List<Variant>? = null,
    val assetType : AssetType,
    val feedItem : FeedItemUI,
    val productId: String
)