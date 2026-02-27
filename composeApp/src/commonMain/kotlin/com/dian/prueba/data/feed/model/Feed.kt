package com.dian.prueba.data.feed.model

import androidx.compose.runtime.Immutable
import com.dian.prueba.data.feed.enums.AssetMediaType
import com.dian.prueba.data.feed.mapper.FeedItemUI
import com.dian.prueba.data.nuFeed.model.Variant
import kotlinx.serialization.Serializable

@Serializable
data class FeedResponse(
    val data: FeedData
)

@Serializable
data class FeedData(
    val feed: List<FeedItemUI>
)

@Immutable
@Serializable
data class AssetResponse(
    val url: String? = null, // En caso de que sea un video tendrá la URL en asset directamente
    val type: AssetMediaType,
    val variants: List<Variant>? = emptyList(),
    val posterVariants: List<Variant>? = emptyList()
)