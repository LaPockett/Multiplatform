package com.dian.prueba.data.modelProduct

import kotlinx.serialization.Serializable

@Serializable
data class VariantX(
    val fitIn: Boolean,
    val size: String,
    val url: String
)