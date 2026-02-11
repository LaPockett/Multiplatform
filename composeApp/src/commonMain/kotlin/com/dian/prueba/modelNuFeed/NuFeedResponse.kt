package com.dian.prueba.modelNuFeed

import kotlinx.serialization.Serializable
import androidx.compose.runtime.Immutable

@Immutable
@Serializable
data class NuFeedResponse(
    val feed: List<Feed>,
    val has_more: Boolean,
    val next_index: Int
)
