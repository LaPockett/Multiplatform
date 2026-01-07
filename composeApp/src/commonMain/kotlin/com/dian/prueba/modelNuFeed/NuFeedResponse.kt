package com.dian.prueba.modelNuFeed

import kotlinx.serialization.Serializable

@Serializable
data class NuFeedResponse(
    val feed: List<Feed>,
    val has_more: Boolean,
    val next_index: Int
)
