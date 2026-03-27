package com.dian.prueba.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.dian.prueba.liquidglass.components.LiquidButton
import com.dian.prueba.data.globalResources.LocalPadding
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.button_msg1
import multiplatform.composeapp.generated.resources.title_feed
import org.jetbrains.compose.resources.stringResource

@Composable
fun HeaderFeedLogo() {
    val paddingModifier = LocalPadding.current

    MultiplatformTheme {
        val backdrop = rememberLayerBackdrop()
        Column(
            modifier = Modifier.padding(top = paddingModifier.small)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .layerBackdrop(backdrop)
                    ) {
                        AsyncImage(
                            model = Res.getUri("files/border.svg"),
                            contentDescription = "Border SVG",
                            modifier = Modifier.fillMaxSize()
                                .align(Alignment.Center)
                        )
                        AsyncImage(
                            model = Res.getUri("files/banner.svg"),
                            contentDescription = "Banner SVG",
                            modifier = Modifier
                                .graphicsLayer {
                                    translationY = -8.dp.toPx()
                                }
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.White.copy(alpha = 0.1f),
                                            Color.White.copy(alpha = 0.2f),
                                            Color.White.copy(alpha = 0.3f),
                                            Color.Black.copy(alpha = 0.1f),
                                            Color.Black.copy(alpha = 0.2f)
                                        )
                                    ),
                                    shape = RoundedCornerShape(bottomStart = 29.2.dp, bottomEnd = 29.2.dp)
                                )
                                .align(Alignment.TopCenter),
                            // imageLoader = getImageLoader()
                        )
                    }
                }
                LiquidButton(
                    onClick = { /* POR IMPLEMENTAR */ },
                    backdrop = backdrop,
                    height = 28.dp,
                    surfaceColor = Color.White.copy(0.3f),
                    modifier = Modifier
                        .graphicsLayer {
                            translationX = -15.dp.toPx()
                            translationY = -22.dp.toPx()
                        }
                        .align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = stringResource(Res.string.button_msg1),
                        fontSize = 10.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Text(
                text = stringResource(Res.string.title_feed),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = paddingModifier.extraTiny),
            )
            // Here Search Bar with animated placeholder
            /*SearchBar(
                query = "",
                onQueryChange = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(bottom = paddingModifier.tiny))*/
        }
    }
}