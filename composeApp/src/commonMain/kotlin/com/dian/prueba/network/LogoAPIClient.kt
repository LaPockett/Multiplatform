package com.dian.prueba.network

import androidx.compose.ui.text.intl.Locale
import com.dian.prueba.data.feed.model.FeedResponse
import com.dian.prueba.domain.feed.model.ProductUIModel
import com.dian.prueba.data.featureFlag.model.FeatureFlagsResponse
import com.dian.prueba.data.nuFeed.mapper.toProductUIModel
import com.dian.prueba.data.nuFeed.model.NuFeedResponse
import com.dian.prueba.data.product.mapper.toDetailUIModel
import com.dian.prueba.data.product.model.ProductDetail
import com.dian.prueba.data.tokens.model.Tokens
import com.dian.prueba.domain.product.model.ProductDetailUIModel
import com.dian.prueba.network.service.UserAPIService
import com.dian.prueba.network.service.FeedAPIService
import com.dian.prueba.network.service.NuFeedAPIService
import com.dian.prueba.network.service.ProductAPIService
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.TokenStorage
import com.dian.prueba.utilities.UnauthorizedException
import com.dian.prueba.viewModel.AuthController
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.*
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class LogoAPIClient(
    private val tokenStorage: TokenStorage
) : FeedAPIService, NuFeedAPIService, ProductAPIService, UserAPIService {

    private val authController = AuthController(tokenStorage)
    private val logger = Logger("LogoAPIClient")
    private val currentLanguage = Locale.current.language

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        defaultRequest {
            header("Accept-Language", currentLanguage)
        }
        HttpResponseValidator {
            validateResponse { response ->
                if (response.status == HttpStatusCode.Unauthorized) {
                    throw UnauthorizedException()
                }
            }
        }

    }

    private suspend fun HttpRequestBuilder.withAuth() {
        val token = authController.getValidToken()
        print("Bearer $token")
        header("Authorization", "Bearer $token")
    }

    private suspend fun <T> retryWithAuth(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: UnauthorizedException) {
            logger.warn("401 recibido: $e")
            authController.forceRefresh()
            block()
        }
    }

    override suspend fun getProductList(): List<ProductUIModel> =
        withContext(Dispatchers.IO) {
            logger.warn("Enter to getProductList")
            retryWithAuth {
                try {
                    val response = client
                        .get("http://192.168.10.130:8160/feed") {
                            withAuth()
                        }
                        .body<FeedResponse>()

                    response.data.feed.mapNotNull { it.toProductUIModel() }
                } catch (e: Exception) {
                    logger.error(e)
                    emptyList()
                }
            }

        }

    override suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse =
        withContext(Dispatchers.IO) {
            retryWithAuth {
                logger.warn("Enter to getNuFeed")
                try {
                    client
                        .get("http://192.168.10.130:8160/nufeed") {
                            withAuth()
                            parameter("paginationIndex", paginationIndex)
                        }
                        .body()
                } catch (e: Exception) {
                    logger.error(e)
                    throw e
                }
            }

        }

    override suspend fun getProductById(productId: String): ProductDetailUIModel? =
        withContext(Dispatchers.IO) {
            retryWithAuth {
                logger.warn("Enter to getProductById")
                try {
                    val response = client
                        .get("http://192.168.10.130:8160/product/$productId"){withAuth()}
                        .body<ProductDetail>()

                    response.data.product.toDetailUIModel()
                } catch (e: Exception) {
                    logger.error(e)
                    throw e
                }
            }

        }

    override suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse =
        withContext(Dispatchers.IO) {
            retryWithAuth {
                logger.warn("Enter to getFeatureFlags")
                try {
                    client
                        .get("http://192.168.10.130:8160/ux/$userId"){withAuth()}
                        .body()
                } catch (e: Exception) {
                    logger.error(e)
                    throw e
                }
            }

        }

    override suspend fun getCurrentRoute(currentRoute: String?, userId: String): String =
        withContext(Dispatchers.IO) {
            retryWithAuth {
                logger.warn("Enter to getCurrentRoute")
                try {
                    logger.warn("Current route: $currentRoute")
                    client
                        .get("http://192.168.10.130:8160/ux/$userId") {
                            withAuth()
                            parameter("currentRoute", currentRoute)
                        }
                        .body()
                } catch (e: Exception) {
                    logger.error(e)
                    throw e
                }
            }

        }

    override suspend fun login(user: String, password: String): Tokens {
        //TODO!: POST /auth/login → guardar tokens reales cuando se pueda
        authController.getValidToken()
        return tokenStorage.loadTokens()!!
    }
}
