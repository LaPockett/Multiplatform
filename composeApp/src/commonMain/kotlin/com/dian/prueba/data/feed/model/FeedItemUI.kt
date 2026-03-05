package com.dian.prueba.data.feed.model

data class FeedItemUI(
    val isPremium: Boolean,
    val isFavorite: Boolean,
    val assetUrl: String,
    val productId: String,
    val variantId: String
)
