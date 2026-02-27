package com.dian.prueba.data.unused

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    val results: List<PokemonListItem>
)

@Serializable
data class PokemonListItem(
    val name: String,
    val url: String
)

data class PokemonUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String
)
