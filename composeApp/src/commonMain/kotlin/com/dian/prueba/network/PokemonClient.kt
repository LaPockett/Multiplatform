package com.dian.prueba.network

import com.dian.prueba.data.model.PokemonListItem
import com.dian.prueba.data.model.PokemonListResponse
import com.dian.prueba.data.model.PokemonUiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    //val sprites : Sprites
)

/*@Serializable
data class Sprites(
    val back_default: String? = null,
    val back_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null,
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
)*/

interface PokemonAPIService {
    suspend fun getPokemon(id: Int): Pokemon?
    suspend fun getPokemonList(limit: Int = 400): List<PokemonUiModel>
}

class PokemonAPIClient : PokemonAPIService {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getPokemon(id: Int): Pokemon? {
        return try {
            client
                .get("https://pokeapi.co/api/v2/pokemon/$id/")
                .body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun PokemonListItem.id(): Int =
        url.trimEnd('/').substringAfterLast('/').toInt()

    override suspend fun getPokemonList(limit: Int): List<PokemonUiModel> {
        return try {
            val response = client
                .get("https://pokeapi.co/api/v2/pokemon") {
                    url { parameters.append("limit", limit.toString()) }
                }
                .body<PokemonListResponse>()

            response.results.map {
                val id = it.id()
                PokemonUiModel(
                    id = id,
                    name = it.name,
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

/*fun main() = runBlocking {
    val api = PokemonAPIClient()
    val pokemon = api.getPokemon(3)
    println("Resultado de la pokeapi: $pokemon")
}*/
