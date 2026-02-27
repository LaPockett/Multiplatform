package com.dian.prueba.data.nuFeed.model

import androidx.compose.runtime.Immutable
import com.dian.prueba.data.feed.model.AssetResponse
import com.dian.prueba.data.nuFeed.enums.AssetType
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