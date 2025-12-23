package com.dian.prueba.network

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dian.prueba.model.LocalColors
import com.dian.prueba.model.LocalPadding
import com.dian.prueba.utilities.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.pokeball
import org.jetbrains.compose.resources.painterResource

@Serializable
data class FeedResponse(
    val data: FeedData
)

@Serializable
data class FeedData(
    val feed: List<FeedItem>
)

@Serializable
data class FeedItem(
    val isPremium: Boolean,
    val isFavorite: Boolean,
    val asset: AssetResponse
)

@Serializable
data class AssetResponse(
    val type: AssetType,
    val variants: List<Variant>? = null
)

@Serializable
data class Variant(
    val url: String
)

@Serializable
enum class AssetType {
    IMAGE,
    VIDEO
}
data class ProductUIModel(
    val imageUrl: String
)

interface LogoAPIService{
    suspend fun getProductList(): List<ProductUIModel>
}

class LogoAPIClient : LogoAPIService {
    private val logger : Logger = Logger("LogoAPIClient")
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
                .filter { it.asset.type == AssetType.IMAGE }
                .mapNotNull { item ->
                    val firstVariant = item.asset.variants?.firstOrNull()
                    firstVariant?.let {
                        ProductUIModel(imageUrl = it.url)
                    }
                }

        } catch (e: Exception) {
            e.printStackTrace()
            print("Error en Api Logo Client: $e")
            emptyList()
        }
    }

}
fun main() = runBlocking {
    val api = LogoAPIClient()
    val list = api.getProductList()
    println("Resultado de la logoapi: $list")
}

@Composable
fun ProductItem(product: ProductUIModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = "Product ${product.imageUrl}",
                placeholder = painterResource(Res.drawable.pokeball),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            /*Text(
                text = product.imageUrl,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )*/
        }
    }
}
@Composable
fun ProductLogoAPI(paddingValues: PaddingValues) {
    val paddingModifier = LocalPadding.current
    val colorModifier = LocalColors.current
    val api = remember { LogoAPIClient() }

    val products = remember { mutableStateListOf<ProductUIModel>() }

    LaunchedEffect(Unit) {
        products.addAll(
            api.getProductList()
        )
    }

    Box(
        modifier = Modifier
            .background(colorModifier.backgroundApp)
            .padding(horizontal = paddingModifier.tiny)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(paddingModifier.extraTiny),
            verticalArrangement = Arrangement.spacedBy(paddingModifier.extraTiny),
            modifier = Modifier
                .background(colorModifier.backgroundApp)
                .fillMaxSize()
        ) {
            items(items = products, key = {product -> product.imageUrl}) { index ->
                ProductItem(product = index)
            }

        }
    }
}


