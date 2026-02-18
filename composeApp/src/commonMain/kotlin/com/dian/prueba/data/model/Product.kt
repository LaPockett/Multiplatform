package com.dian.prueba.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val product: String,
    val variant: String
)