package com.dian.prueba.data.modelProduct

import kotlinx.serialization.Serializable

@Serializable
data class Picture(
    val __typename: String,
    val _id: String,
    val bucket: String,
    val key: String,
    val meta: Meta,
    val props: Props?,
    val type: String,
    val variants: List<VariantX>
)