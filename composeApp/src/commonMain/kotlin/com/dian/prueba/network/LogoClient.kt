package com.dian.prueba.network

import androidx.compose.ui.text.intl.Locale
import com.dian.prueba.model.*
import com.dian.prueba.modelFeatureFlag.FeatureFlagsResponse
import com.dian.prueba.modelNuFeed.NuFeedResponse
import com.dian.prueba.modelProduct.ProductDetail
import com.dian.prueba.modelProduct.ProductDetailUIModel
import com.dian.prueba.utilities.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

interface LogoAPIService {
    suspend fun getProductList(): List<ProductUIModel>
    suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse
    suspend fun getProductById(productId: String): ProductDetailUIModel?
    suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse
    suspend fun setFeatureFlag(userId: String, flagName: String, enabled: Boolean): FeatureFlagsResponse
    suspend fun handleFeatureFlags(userId: String, flagName: String, enabled: Boolean)
}

class LogoAPIClient : LogoAPIService {
    private val logger: Logger = Logger("LogoAPIClient")
    private val currentLanguage = Locale.current.language
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            header("Accept-Language", currentLanguage)
        }
    }

    override suspend fun getProductById(productId: String): ProductDetailUIModel? = withContext(Dispatchers.IO) {
        logger.warn("Enter to GetProductById")
        try {
            val response = client
                .get("http://192.168.10.130:8160/product/$productId")
                .body<ProductDetail>()
            return@withContext ProductDetailUIModel(
                _id = response.data.product._id,
                brand = response.data.product.brand,
                manufacturingCountry = response.data.product.manufacturingCountry,
                productName = response.data.product.productName,
                storyTelling = response.data.product.storyTelling,
                styleIt = response.data.product.styleIt,
                type = response.data.product.type,
                variants = response.data.product.variants
            )
        } catch (e: Exception) {
            e.printStackTrace()
            print("Error en Api Logo Client obteniendo el producto: $e")
            throw e
        }
    }

    override suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse = withContext(Dispatchers.IO){
        logger.warn("Enter to GetFeatureFlags")
        try {
            val response = client
                .get("http://127.0.0.1:8160/ux/$userId")
                .body<FeatureFlagsResponse>()
            return@withContext response

        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun setFeatureFlag(
        userId: String,
        flagName: String,
        enabled: Boolean
    ): FeatureFlagsResponse = withContext(Dispatchers.IO){
        logger.warn("Enter to SetFeatureFlag: $flagName = $enabled")
        try {
            val endpointName = "set${flagName.capitalize()}"
            val response = client
                .get("http://192.168.10.130:8160/ux/$userId/$endpointName/$enabled")
                .body<FeatureFlagsResponse>()
            return@withContext response
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun handleFeatureFlags(
        userId: String,
        flagName: String,
        enabled: Boolean
    ) {
        logger.warn("Enter to HandleFeatureFlags")
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        try {
            scope.launch {
                while (true) {
                    val response = setFeatureFlag(userId, flagName, enabled)
                    println("Feature flags class: $response")
                    delay(10000)
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Mapeando los datos en el servicio de la API
     */
    override suspend fun getProductList(): List<ProductUIModel> = withContext(Dispatchers.IO) {
        logger.warn("Enter to GetProductList")

        return@withContext try {
            val response = client
                .get("http://192.168.10.130:8160/feed")
                .body<FeedResponse>()
            response.data.feed
                .mapNotNull { item ->
                    when (item.asset.type) {
                        AssetMediaType.IMAGE -> {
                            val image = item.asset.variants?.firstOrNull()?.url
                            image?.let {
                                ProductUIModel(
                                    imageUrl = it,
                                    assetType = AssetMediaType.IMAGE,
                                    feedItem = item,
                                    productId = item.product.product
                                )
                            }
                        }

                        AssetMediaType.VIDEO -> {
                            val videoId = item.asset.posterVariants?.firstOrNull()?.url
                            videoId?.let {
                                ProductUIModel(
                                    assetType = AssetMediaType.VIDEO,
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
                .get("http://192.168.10.130:8160/nufeed") {
                    parameter("paginationIndex", paginationIndex)
                }
                .body()
        } catch (e: Exception) {
            logger.error(e)
            throw e
        }
    }
}

private fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

/*fun main() = runBlocking {
    val api = LogoAPIClient()
    val list = api.getProductList()
    println("Resultado de la logoapi: $list")
}*/