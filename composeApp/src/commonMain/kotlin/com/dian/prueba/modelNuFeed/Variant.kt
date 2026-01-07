package com.dian.prueba.modelNuFeed

import kotlinx.serialization.Serializable

@Serializable
data class Variant(
    val fitIn: Boolean,
    val size: String,
    val url: String
)