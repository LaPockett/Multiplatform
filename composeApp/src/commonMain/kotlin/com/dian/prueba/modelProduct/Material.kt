package com.dian.prueba.modelProduct

import kotlinx.serialization.Serializable


@Serializable
data class Material(
    val brand: String,
    val brandName: String,
    val colorName: String,
    val colors: List<String>,
    val finish: String,
    val genericName: String,
    val hardwareColor: String,
    val type: String
)