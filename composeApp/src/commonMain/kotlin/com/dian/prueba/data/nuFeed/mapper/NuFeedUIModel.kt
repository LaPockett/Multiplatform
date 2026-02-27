package com.dian.prueba.data.nuFeed.mapper

import androidx.compose.runtime.Immutable
import com.dian.prueba.data.feed.enums.AssetMediaType
import com.dian.prueba.data.nuFeed.model.Variant

@Immutable
sealed class NuFeedUIModel {
    @Immutable
    data class MessageOut(
        val text: String
    ): NuFeedUIModel()

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

    data class Asset(
        val url: String? = null,
        val type: AssetMediaType,
        val variants: List<Variant> = emptyList(),
        val posterVariants: List<Variant>? = emptyList()
    )
}

