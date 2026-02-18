package com.dian.prueba.data.modelProduct

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val colors: List<String>,
    val height: Int,
    val size: Int,
    val width: Int
)