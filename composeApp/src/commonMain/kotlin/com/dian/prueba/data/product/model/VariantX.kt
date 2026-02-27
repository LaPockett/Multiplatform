package com.dian.prueba.data.product.model

import kotlinx.serialization.Serializable

@Serializable
data class VariantX(
    val fitIn: Boolean,
    val size: String,
    val url: String
)