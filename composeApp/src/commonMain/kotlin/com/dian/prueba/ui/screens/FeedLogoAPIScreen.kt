package com.dian.prueba.ui.screens

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.dian.prueba.model.ProductUIModel
import com.dian.prueba.network.LogoAPIClient
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.pokeball
import org.jetbrains.compose.resources.painterResource

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

            Text(
                text = product.assetType.toString(),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        }
    }
}
@Composable
fun FeedLogoApiScreen(paddingValues: PaddingValues) {
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