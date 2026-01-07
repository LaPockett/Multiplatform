package com.dian.prueba.modelNuFeed

import kotlinx.serialization.Serializable

@Serializable
data class Feed(
    val type: TypeAsset,
    val body: String? = null,
    val actions: List<Mood> = emptyList(),
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
        val actions: List<Mood>
    ) : NuFeedUIModel()

    data class Tile(
        val imageUrl: String,
        val isPremium: Boolean,
        val isFavorite: Boolean,
        val productId: String
    ) : NuFeedUIModel()
}

