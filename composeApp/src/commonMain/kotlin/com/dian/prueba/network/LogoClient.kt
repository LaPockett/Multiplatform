package com.dian.prueba.network

import com.dian.prueba.model.*
import com.dian.prueba.utilities.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

interface LogoAPIService {
    suspend fun getProductList(): List<ProductUIModel>
}

class LogoAPIClient : LogoAPIService {
    private val logger: Logger = Logger("LogoAPIClient")
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getProductList(): List<ProductUIModel> {
        logger.warn("Enter to GetProductList")

        return try {
            val response = client
                .get("http://192.168.10.209:8160/feed")
                .body<FeedResponse>()
            response.data.feed
                .mapNotNull { item ->
                    when (item.asset.type) {
                        AssetType.IMAGE -> {
                            val image = item.asset.variants?.firstOrNull()?.url
                            image?.let {
                                ProductUIModel(
                                    imageUrl = it,
                                    assetType = AssetType.IMAGE
                                )
                            }
                        }
                        AssetType.VIDEO -> {
                            val videoId = item.asset.posterVariants?.firstOrNull()?.url
                            videoId?.let {
                                ProductUIModel(
                                    assetType = AssetType.VIDEO,
                                    imageUrl = it,
                                    urlVideo = item.asset.url
                                )
                            }
                        }
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
            print("Error en Api Logo Client: $e")
            emptyList()
        }
    }
}

/*fun main() = runBlocking {
    val api = LogoAPIClient()
    val list = api.getProductList()
    println("Resultado de la logoapi: $list")
}*/