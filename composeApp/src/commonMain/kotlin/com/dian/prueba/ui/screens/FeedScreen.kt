package com.dian.prueba.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dian.prueba.liquidglass.destinations.BottomTabsLiquidGlass
import com.dian.prueba.liquidglass.destinations.GlassClippyLogo
import com.dian.prueba.model.LocalDimension
import com.dian.prueba.navigation.ScreenBottom
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.dian.prueba.ui.splash.FirstSplash
import com.dian.prueba.ui.splash.SecondSplash
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
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
        val uiDimensions = LocalDimension.current
        val hazeState = rememberHazeState()
        val backdrop = rememberLayerBackdrop()
        val navController = rememberNavController()
        val lightAlpha = 0.3f
        val darkAlpha = 0.1f
        val hazeStyle = HazeStyle(
            backgroundColor = Color.White,
            tints = listOf(
                HazeTint(
                    Color.White.copy(alpha = if (Color.White.luminance() >= 0.5) lightAlpha else darkAlpha),
                )
            ),
            blurRadius = 5.dp,
            noiseFactor = -1f,
            fallbackTint = HazeTint.Unspecified,
        )
        Scaffold(
            //modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier
                        .hazeEffect(state = hazeState, style = hazeStyle)
                        .fillMaxWidth()
                        .dropShadow(
                            shape = RoundedCornerShape(20.dp),
                            shadow = Shadow(
                                radius = 8.dp,
                                spread = 3.dp,
                                color = Color(0xd7ffffff),
                                offset = DpOffset(x = 4.dp, 4.dp)
                            )
                        )
                        .height(95.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background,
                                    MaterialTheme.colorScheme.background.copy(alpha = 1f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.9f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.6f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.4f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.1f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0f),
                                ),
                                startY = 245f,
                                endY = 300f,
                            )
                        ),
                    windowInsets = WindowInsets.statusBars,
                    navigationIcon = {},//
                    actions = {},
                    title = {
                        ImageLogo(
                            tint = Color.Black,
                            painter = painterResource(Res.drawable.logotitle),
                            modifier = Modifier.height(48.dp)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),

                    )
            },
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
                        modifier = Modifier.size(uiDimensions.iconNormal)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            containerColor = Color.Transparent,
            bottomBar = {
                //GlassmorphicBottomNavigation(hazeState, navController)
                Box(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    BottomTabsLiquidGlass(backdrop, navController)
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = ScreenBottom.Closet.route,
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(hazeState)
                    .layerBackdrop(backdrop)
                //.padding(padding)
            ) {
                composable(ScreenBottom.Newspaper.route) {
                    NewsletterScreen(paddingValues)
                }
                composable(ScreenBottom.Closet.route) {
                    ClosetScreen(paddingValues)
                }
                composable(ScreenBottom.Profile.route) {
                    ProfileScreen(paddingValues)
                }
            }
        }
    }
}

@Composable
fun ImageLogo(
    painter: Painter = painterResource(Res.drawable.logo),
    tint: Color,
    modifier: Modifier = Modifier
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
@Composable
fun RootNavigation2() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.First) }
    var nextScreen by remember { mutableStateOf<Screen?>(null) }
    val isAnimating = nextScreen != null

    val offsetY by animateDpAsState(
        targetValue = if (isAnimating) (-990).dp else 0.dp,
        animationSpec = tween(1500),
        finishedListener = {
            if (isAnimating) {
                currentScreen = nextScreen!!
                nextScreen = null
            }
        }
    )
    Box(Modifier.fillMaxSize()) {
        // Static screen
        if (nextScreen != null) {
            ScreenContent(
                screen = nextScreen!!,
                onNavigate = { /* No navigation while the navigation*/ }
            )
        } else {
            ScreenContent(
                screen = currentScreen,
                onNavigate = { target ->
                    if (!isAnimating) {
                        nextScreen = target
                    }
                }
            )
        }
        // Animated screen
        if (isAnimating) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = offsetY)
            ) {
                ScreenContent(
                    screen = currentScreen,
                    onNavigate = {}
                )
            }
        }
    }
}
sealed class Screen {
    object First : Screen()
    object Second : Screen()
    object Main : Screen()
}

@Composable
fun ScreenContent(screen: Screen, onNavigate: (Screen) -> Unit = {}) {
    when (screen) {
        Screen.First -> FirstSplash { onNavigate(Screen.Second) }
        Screen.Second -> SecondSplash{ onNavigate(Screen.Main) }
        Screen.Main -> LogoNavigation()
    }
}