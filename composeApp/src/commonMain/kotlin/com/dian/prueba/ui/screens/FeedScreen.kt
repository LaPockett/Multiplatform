package com.dian.prueba.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dian.prueba.liquidglass.destinations.BottomTabsLiquidGlass
import com.dian.prueba.liquidglass.destinations.GlassClippyLogo
import com.dian.prueba.navigation.ScreenBottom
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.rememberHazeState
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.logo
import multiplatform.composeapp.generated.resources.logotitle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Preview
@Composable
fun LogoNavigation() {
    MultiplatformTheme {
        val hazeState = rememberHazeState()
        val backdrop = rememberLayerBackdrop()
        val navController = rememberNavController()
        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            floatingActionButton = {
                GlassClippyLogo(
                    onClick = {},
                    backdrop = backdrop,
                    surfaceColor = Color.DarkGray.copy(0.2f),
                    sizeClippy = 50.dp
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.logo),
                        contentDescription = "Logo",
                        tint = Color.White,
                        modifier = Modifier.size(23.dp)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                //GlassmorphicBottomNavigation(hazeState, navController)
                Box(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    BottomTabsLiquidGlass(backdrop, navController)
                }
            },
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.fillMaxWidth()
                        //.padding(WindowInsets.safeDrawing.asPaddingValues())
                        .height(78.dp)
                        /*.hazeEffect(
                            state = hazeState,
                            style = hazeStyle,
                        )*/
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background,
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.99f),
                                    Color.Transparent
                                ),
                                startY = 120f,
                                endY = 140f,
                            )
                            //Color.Red
                        ),
                    windowInsets = WindowInsets.statusBars,
                    colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background),
                    navigationIcon = {},//
                    actions = {},
                    title = {
                        ImageLogo(
                            tint = Color.Black,
                            painter = painterResource(Res.drawable.logotitle),
                            modifier = Modifier.size(78.dp)
                        )
                    }
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = ScreenBottom.Closet.route,
                modifier = Modifier
                    .fillMaxSize()
                    .layerBackdrop(backdrop)
                    .hazeSource(hazeState)
                //.padding(padding)
            ) {
                composable(ScreenBottom.Newspaper.route) { NewsletterScreen(paddingValues) }
                composable(ScreenBottom.Closet.route) { ClosetScreen(paddingValues) }
                composable(ScreenBottom.Profile.route) { ProfileScreen(paddingValues) }
            }
        }
    }
}

@Composable
fun ImageLogo(
    painter: Painter = painterResource(Res.drawable.logo),
    tint: Color,
    modifier : Modifier = Modifier
) {
    Icon(
        painter = painter,
        contentDescription = "Logo de Logo Circular Luxury Closet",
        modifier = modifier,
        tint = tint
    )
}

/**
 * Clippy Logo with Haze
 */
@Composable
fun ClippyLogo(
    hazeState: HazeState,
    hazeStyle: HazeStyle
) {
    Box(
        modifier = Modifier.graphicsLayer {
            translationX = 4.4.dp.toPx()
            translationY = 12.dp.toPx()
        }
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(45.dp)
                .background(Color.Transparent)
                .hazeEffect(state = hazeState, style = hazeStyle)
                .clickable { /* POR IMPLEMENTAR */ },
            contentAlignment = Alignment.Center
        ) {
            ImageLogo(tint = Color.White, modifier = Modifier.size(24.dp))
        }
    }
}