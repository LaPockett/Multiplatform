package com.dian.prueba.modelNuFeed

import com.dian.prueba.model.AssetMediaType
import kotlinx.serialization.Serializable

@Serializable
data class Feed(
    val type: AssetType,
    val typeMedia: AssetMediaType = AssetMediaType.IMAGE,
    val body: String? = null,
    val actions: List<String> = emptyList(),
    val product: Product? = null,
    val asset: Asset? = null,
    val large: Boolean? = null,
    val isPremium: Boolean? = null,
    val isFavorite: Boolean? = null
)

sealed class NuFeedUIModel {
    data class MessageOut(
        val text: String
    ): NuFeedUIModel()

    data class MessageIn(
        val text: String,
        val actions: List<String>
    ) : NuFeedUIModel()

    data class Tile(
        val imageUrl: String,
        val isPremium: Boolean,
        val isFavorite: Boolean,
        val productId: String
    ) : NuFeedUIModel()

    data class Asset(
        val url: String? = null,
        val type: AssetMediaType,
        val variants: List<Variant> = emptyList(),
        val posterVariants: List<Variant>? = emptyList()
    )
}

