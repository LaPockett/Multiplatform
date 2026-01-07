package com.dian.prueba.network

import com.dian.prueba.model.*
import com.dian.prueba.modelNuFeed.NuFeedResponse
import com.dian.prueba.utilities.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

interface LogoAPIService {
    suspend fun getProductList(): List<ProductUIModel>
    suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse
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

    /**
     * Mapeando los datos en el servicio de la API
     */
    override suspend fun getProductList(): List<ProductUIModel> = withContext(Dispatchers.IO) {
        logger.warn("Enter to GetProductList")

        return@withContext try {
            val response = client
                .get("http://192.168.1.141:8160/feed")
                .body<FeedResponse>()
            response.data.feed
                .mapNotNull { item ->
                    when (item.asset.type) {
                        AssetType.IMAGE -> {
                            val image = item.asset.variants?.firstOrNull()?.url
                            image?.let {
                                ProductUIModel(
                                    imageUrl = it,
                                    assetType = AssetType.IMAGE,
                                    feedItem = item,
                                    productId = item.product.product
                                )
                            }
                        }

                        AssetType.VIDEO -> {
                            val videoId = item.asset.posterVariants?.firstOrNull()?.url
                            videoId?.let {
                                ProductUIModel(
                                    assetType = AssetType.VIDEO,
                                    imageUrl = it,
                                    urlVideo = item.asset.url,
                                    feedItem = item,
                                    productId = item.product.product
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
    override suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse = withContext(Dispatchers.IO){
        logger.warn("Enter to getNuFeed")
        try {
            client
                .get("http://192.168.1.141:8160/nufeed") {
                    parameter("paginationIndex", paginationIndex)
                }
                .body()
        } catch (e: Exception) {
            logger.error(e)
            throw e
        }
    }
}

/*fun main() = runBlocking {
    val api = LogoAPIClient()
    val list = api.getProductList()
    println("Resultado de la logoapi: $list")
}*/