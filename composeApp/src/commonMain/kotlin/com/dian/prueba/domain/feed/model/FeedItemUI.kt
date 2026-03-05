package com.dian.prueba.domain.feed.model

data class FeedItemUI(
    val isPremium: Boolean,
    val isFavorite: Boolean,
    val assetUrl: String,
    val productId: String,
    val variantId: String
)