package com.dian.prueba.data.product.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val _id: String,
    val brand: String,
    val manufacturingCountry: String,
    val productName: String,
    val storyTelling: String,
    val styleIt: String,
    val type: String,
    val variants: List<Variant>
)