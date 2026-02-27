package com.dian.prueba.data.product.model

import kotlinx.serialization.Serializable

@Serializable
data class Size(
    val __typename: String,
    val category: String,
    val depth: Double,
    val height: Double,
    val isStrapFoldable: Boolean,
    val isStrapRemovable: Boolean,
    val strapDrop: String?,
    val strapDropMax: String?,
    val weight: String?,
    val width: Double
)