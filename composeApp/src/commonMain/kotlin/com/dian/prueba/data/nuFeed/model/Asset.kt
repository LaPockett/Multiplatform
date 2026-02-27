package com.dian.prueba.data.nuFeed.model

import com.dian.prueba.data.feed.enums.AssetMediaType
import kotlinx.serialization.Serializable

@Serializable
data class Asset(
    val typeName: String = "None",
    val meta: Meta,
    val props: Props,
    val type: AssetMediaType,
    val variants: List<Variant> = emptyList(),
    val posterVariants : List<Variant> = emptyList()
)