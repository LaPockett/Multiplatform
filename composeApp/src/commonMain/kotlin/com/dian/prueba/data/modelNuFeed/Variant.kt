package com.dian.prueba.data.modelNuFeed

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Variant(
    val fitIn: Boolean,
    val size: String,
    val url: String
)