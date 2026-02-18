package com.dian.prueba.data.modelProduct

import kotlinx.serialization.Serializable

@Serializable
data class Variant(
    val _id: String,
    val careMessage: String,
    val isFavorite: Boolean,
    val material: Material,
    val pictures: List<Picture>,
    val size: Size,
    val subscriptionTier: String
)