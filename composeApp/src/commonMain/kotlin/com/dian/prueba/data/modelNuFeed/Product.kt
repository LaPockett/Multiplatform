package com.dian.prueba.data.modelNuFeed

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val product: String,
    val variant: String
)