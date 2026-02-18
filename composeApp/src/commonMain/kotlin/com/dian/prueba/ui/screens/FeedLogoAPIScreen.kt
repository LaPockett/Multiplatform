package com.dian.prueba.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dian.prueba.data.model.AssetMediaType
import com.dian.prueba.data.model.LocalColors
import com.dian.prueba.data.model.LocalPadding
import com.dian.prueba.data.model.ProductUIModel
import com.dian.prueba.viewModel.FeedVM
import io.github.kdroidfilter.composemediaplayer.rememberVideoPlayerState

@Composable
fun ProductItem(product: ProductUIModel) {
    val playerState = rememberVideoPlayerState()
    val url = product.urlVideo.toString()
    /*LaunchedEffect(url) {
        playerState.volume = 0f
        playerState.openUri(url)
        playerState.loop = true
    }*/
    Card(
        modifier = Modifier
            .fillMaxWidth().height(290.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {

        if (product.assetType == AssetMediaType.VIDEO) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
            ) {
                /*VideoPlayerSurface(
                    playerState = playerState,
                    modifier = Modifier.fillMaxWidth().height(290.dp),
                    contentScale = ContentScale.Crop,
                    overlay = {
                        if (playerState.isLoading) {
                            //Box(
                            //modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                            //) {
                            AsyncImage(
                                model = product.imageUrl,
                                contentDescription = "Poster Video Preview",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            //}
                        }
                    }
                )*/
            }
        } else {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = "Image",
                modifier = Modifier.fillMaxWidth().height(290.dp),
                contentScale = ContentScale.Crop
            )
        }
        /*Text(
            text = product.assetType.toString(),
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        Text(
            text = product.feedItem.isPremium.toString(),
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        Text(
            text = product.feedItem.isFavorite.toString(),
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        Text(
            text = product.feedItem.product.product,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        )*/

    }
}

@Composable
fun FeedLogoApiScreen(
    paddingValues: PaddingValues,
    feedVM: FeedVM
) {
    val paddingModifier = LocalPadding.current
    val colorModifier = LocalColors.current
    val products by feedVM.productList.collectAsState()
    Box(
        modifier = Modifier
            .background(colorModifier.backgroundApp)
            .fillMaxSize()
            .padding(horizontal = paddingModifier.tiny).padding(top = paddingModifier.tiny),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(paddingModifier.extraTiny),
            verticalArrangement = Arrangement.spacedBy(paddingModifier.extraTiny),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(items = products) { index ->
                ProductItem(product = index)
            }

        }
    }
}