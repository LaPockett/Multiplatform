package com.dian.prueba.data.modelNuFeed

import androidx.compose.runtime.Immutable
import com.dian.prueba.data.model.AssetMediaType
import com.dian.prueba.data.model.AssetResponse
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Feed(
    val type: AssetType,
    val body: String? = null,
    val actions: List<String> = emptyList(),
    val product: Product? = null,
    val asset: AssetResponse? = null,
    val large: Boolean? = null,
    val isPremium: Boolean? = null,
    val isFavorite: Boolean? = null
)

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

