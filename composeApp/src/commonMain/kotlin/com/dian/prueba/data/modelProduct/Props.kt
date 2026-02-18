package com.dian.prueba.data.modelProduct

import kotlinx.serialization.Serializable

@Serializable
data class Props(
    val category: String,
    val fileName: String,
    val influencerIgHandle: String? = "nil",
    val seasonTags: List<String>? = emptyList()
)