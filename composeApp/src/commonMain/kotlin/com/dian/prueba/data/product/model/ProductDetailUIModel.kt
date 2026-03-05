package com.dian.prueba.data.product.model

data class ProductDetailUIModel(
    val id: String,
    val brand: String,
    val manufacturingCountry: String,
    val productName: String,
    val storyTelling: String,
    val styleIt: String,
    val type: String,
    val variants: List<Variant>
)