package com.dian.prueba.domain.feed.model

import androidx.compose.runtime.Stable
import com.dian.prueba.data.feed.enums.AssetMediaType
import com.dian.prueba.data.nuFeed.model.Variant

@Stable
data class ProductUIModel(
    val urlVideo: String? = null,
    val imageUrl: String,
    val posterVariants: List<Variant>? = null,
    val assetType: AssetMediaType,
    val feedItem: FeedItemUI,
    val productId: String,
)