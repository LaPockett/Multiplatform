package com.dian.prueba.model

import com.dian.prueba.modelNuFeed.Variant
import kotlinx.serialization.Serializable

@Serializable
data class FeedResponse(
    val data: FeedData
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
    val type: AssetMediaType,
    val variants: List<Variant>? = emptyList(),
    val posterVariants: List<Variant>? = emptyList()
)

@Serializable
enum class AssetMediaType {
    IMAGE,
    VIDEO
}
@Serializable
data class ProductUIModel(
    val urlVideo : String? = null,
    val imageUrl: String,
    val posterVariants: List<Variant>? = null,
    val assetType : AssetMediaType,
    val feedItem : FeedItemUI,
    val productId: String,
)