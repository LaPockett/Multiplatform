package com.dian.prueba.modelNuFeed

import kotlinx.serialization.Serializable

@Serializable
data class Asset(
    val typeName: String = "None",
    val meta: Meta,
    val props: Props,
    val type: String,
    val variants: List<Variant> = emptyList(),
    val posterVariants : List<Variant> = emptyList()
)