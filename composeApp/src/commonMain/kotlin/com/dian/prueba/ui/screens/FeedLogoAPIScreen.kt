package com.dian.prueba.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import chaintech.videoplayer.host.MediaPlayerHost
import chaintech.videoplayer.model.VideoPlayerConfig
import chaintech.videoplayer.ui.video.VideoPlayerComposable
import coil3.compose.AsyncImage
import com.dian.prueba.model.AssetType
import com.dian.prueba.model.LocalColors
import com.dian.prueba.model.LocalPadding
import com.dian.prueba.model.ProductUIModel
import com.dian.prueba.network.LogoAPIClient
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.layerBackdrop

@Composable
fun ProductItem(product: ProductUIModel) {
    /**
     * Como no tenemos autorización para los videos de logo, ponemos este video de ejemplo de pexels:
     */
    val pexelsVideo = "https://videos.pexels.com/video-files/20570352/20570352-hd_720_1280_30fps.mp4"
    val playerHost = remember {
        MediaPlayerHost(
            mediaUrl = pexelsVideo,
            isMuted = true)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (product.assetType == AssetType.VIDEO) {
                VideoPlayerComposable(
                    modifier = Modifier.fillMaxWidth().height(290.dp),
                    playerHost = playerHost,
                    playerConfig = VideoPlayerConfig(
                        showControls = false,
                        isSeekBarVisible = false,
                        seekBarBottomPadding = 0.dp,
                        seekBarBottomPaddingInFullScreen = 0.dp,
                        isZoomEnabled = false,
                        isGestureVolumeControlEnabled = false,
                        enableFullEdgeToEdge = true,
                        loadingIndicatorColor = Color.Transparent
                    )
                )
            } else {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = "Product ${product.imageUrl}",
                    modifier = Modifier
                        .fillMaxWidth(),
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
            )*/
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
            items(items = products, key = {product -> product.imageUrl}) { index ->
                ProductItem(product = index)
            }

        }
    }
}