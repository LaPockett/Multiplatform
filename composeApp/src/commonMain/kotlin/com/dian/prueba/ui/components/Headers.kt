package com.dian.prueba.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.dian.prueba.liquidglass.components.LiquidButton
import com.dian.prueba.model.LocalPadding
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.dian.prueba.ui.screens.ImageLogo
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.logotitle
import org.jetbrains.compose.resources.painterResource

@Composable
fun HeaderLogo() {
    MultiplatformTheme {
        val hazeState = rememberHazeState()
        val backdrop = rememberLayerBackdrop()
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().layerBackdrop(backdrop),
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        AsyncImage(
                            model = Res.getUri("files/border.svg"),
                            contentDescription = "Border SVG",
                            modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter),
                        )
                        AsyncImage(
                            model = Res.getUri("files/banner.plain.svg"),
                            contentDescription = "Banner SVG",
                            modifier = Modifier
                                .hazeSource(hazeState)
                                .fillMaxSize()
                                .align(Alignment.TopCenter),
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .zIndex(2f)
                        .graphicsLayer {
                            translationX = 140.dp.toPx()
                            translationY = 50.dp.toPx()
                        },
                    contentAlignment = Alignment.TopCenter
                ) {
                    LiquidButton(
                        onClick = { /* POR IMPLEMENTAR */ },
                        backdrop = backdrop,
                        surfaceColor = Color.White.copy(0.3f),
                        modifier = Modifier
                            .height(32.dp)
                            .width(88.dp)
                    ) {
                        Text(
                            text = "View Post",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Your daily inspiration",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 10.dp),
                )
            }
            // Here Search Bar with animated placeholder
            SearchBar(
                query = "",
                onQueryChange = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun HeaderLogo2() {
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
                            .layerBackdrop(backdrop),
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
                                .align(Alignment.TopCenter),
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
                        //.height(28.dp)
                        .width(92.dp).align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = "VIEW POST",
                        fontSize = 10.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Text(
                text = "Your daily inspiration",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = paddingModifier.extraTiny),
            )
            // Here Search Bar with animated placeholder
            SearchBar(
                query = "",
                onQueryChange = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(bottom = paddingModifier.tiny))
        }
    }
}