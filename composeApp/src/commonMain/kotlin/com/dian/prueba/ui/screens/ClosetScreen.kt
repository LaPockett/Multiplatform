package com.dian.prueba.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.rememberHazeState
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.lechiquito
import multiplatform.composeapp.generated.resources.logotitle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Preview
@Composable
fun ClosetScreen() {
    val lightAlpha = 0.3f
    val darkAlpha = 0.1f
    val hazeState = rememberHazeState()
    val hazeStyle = HazeStyle(
        backgroundColor = Color.White,
        tints = listOf(
            HazeTint(
                Color.White.copy(alpha = if (Color.White.luminance() >= 0.5) lightAlpha else darkAlpha),
            )
        ),
        blurRadius = 10.dp,
        noiseFactor = -1f,
        fallbackTint = HazeTint.Unspecified,
    )
    /*val hazeStyle2 = HazeStyle(
        backgroundColor = Color.White,
        tints = listOf(
            HazeTint(
                Color.White.copy(alpha = if (Color.White.luminance() >= 0.5) lightAlpha else darkAlpha),
            )
        ),
        blurRadius = 3.dp,
        noiseFactor = -1f,
        fallbackTint = HazeTint.Unspecified,
    )*/
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)//.windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = 12.dp)//.statusBarsPadding(),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier
                .hazeSource(hazeState)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize(),
            //contentPadding = PaddingValues(top = 70.dp, bottom = 24.dp)
        ) {
            item(
                span = { GridItemSpan(2) }
            ) {
                // Header of the feed
                HeaderLogo()
            }
            items(22) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(6.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Item $it",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Image(
                            painter = painterResource(Res.drawable.lechiquito),
                            contentDescription = "Logo de Amazon",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
        // RESOLVER LA VISTA DEL BLUR DEL CLIPPY LOGO
        /*val interactionSource = remember { MutableInteractionSource() }
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier.size(50.dp) // Sí que está pero detrás del bottom navigation
                .align(Alignment.Center)
                //.padding(bottom = 5.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { }
                )
        ) {
            Box(
                modifier = Modifier.fillMaxSize().hazeEffect(
                    state = hazeState,
                    style = hazeStyle2
                ),
                contentAlignment = Alignment.Center
            ) {

                ImageLogo(tint = Color.White, size = 30.dp)

            }
        }*/
    }
    Box(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing),
        contentAlignment = Alignment.TopCenter
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier.fillMaxWidth().height(45.dp)
                .hazeEffect(
                    state = hazeState,
                    style = hazeStyle,
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(MaterialTheme.colorScheme.background, Color.Transparent),
                        startY = 95f,
                        endY = 300f,
                    )
                ),
            windowInsets = WindowInsets(0, 0, 0, 0),
            colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
            navigationIcon = {},
            actions = {},
            title = {
                ImageLogo(tint = Color.Black, painter = painterResource(Res.drawable.logotitle), size = 60.dp)

            }
        )
    }
}