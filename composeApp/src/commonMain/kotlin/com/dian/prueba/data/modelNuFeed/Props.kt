package com.dian.prueba.data.modelNuFeed

import kotlinx.serialization.Serializable

@Serializable
data class Props(
    val category: String,
    val fileName: String,
    val influencerIgHandle: String = "None",
    val seasonTags: List<String> = emptyList(),
)