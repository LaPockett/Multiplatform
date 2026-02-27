package com.dian.prueba.data.nuFeed.model

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val colors: List<String>? = null,
    val height: Int,
    val size: Int,
    val width: Int
)