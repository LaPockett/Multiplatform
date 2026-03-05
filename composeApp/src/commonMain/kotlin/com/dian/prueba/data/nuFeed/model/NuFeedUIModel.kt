package com.dian.prueba.data.nuFeed.model

import androidx.compose.runtime.Immutable
import com.dian.prueba.data.feed.enums.AssetMediaType

@Immutable
sealed class NuFeedUIModel {
    @Immutable
    data class MessageOut(
        val text: String
    ) : NuFeedUIModel()

    @Immutable
    data class MessageIn(
        val text: String,
        val actions: List<String>
    ) : NuFeedUIModel()

    @Immutable
    data class Tile(
        val imageUrl: String,
        val urlVideo: String? = null,
        val isPremium: Boolean,
        val isFavorite: Boolean,
        val productId: String,
        val typeMedia: AssetMediaType,
        val posterVariants: List<Variant>? = null
    ) : NuFeedUIModel()
}