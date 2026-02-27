package com.dian.prueba.data.nuFeed.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Variant(
    val fitIn: Boolean,
    val size: String,
    val url: String
)