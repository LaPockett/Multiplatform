package com.dian.prueba.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.dian.prueba.navigation.GlassmorphicBottomNavigation
import com.dian.prueba.navigation.ScreenBottom
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.dian.prueba.ui.components.SearchBar
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

@Composable
fun HeaderLogo() {
    MultiplatformTheme {
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
            blurRadius = 4.dp,
            noiseFactor = -1f,
            fallbackTint = HazeTint.Unspecified,
        )
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
                .padding(top = 50.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
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
                        .align(Alignment.BottomCenter),
                )
                Box(
                    modifier = Modifier
                        .height(29.dp)
                        .width(80.dp)
                        .graphicsLayer {
                            translationX = 140.dp.toPx()
                            translationY = 50.dp.toPx()
                        }
                        .clip(RoundedCornerShape(50.dp))
                        .fillMaxSize()
                        .hazeEffect(state = hazeState, style = hazeStyle)
                        .clickable { /* POR IMPLEMENTAR */ },

                    contentAlignment = Alignment.Center
                ) {
                    //ImageLogo(tint = Color.White, size = 25.dp)
                    Text(
                        text = "VIEW POST",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Preview
@Composable
fun LogoNavigation() {
    MultiplatformTheme {
        val hazeState = rememberHazeState()
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
            blurRadius = 3.dp,
            noiseFactor = -1f,
            fallbackTint = HazeTint.Unspecified,
        )
        Scaffold(
            floatingActionButton = {
                ClippyLogo(hazeState, hazeStyle)
            },
            floatingActionButtonPosition = FabPosition.End,
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                GlassmorphicBottomNavigation(hazeState, navController)
            },
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.fillMaxWidth()
                        .padding(WindowInsets.safeDrawing.asPaddingValues())
                        .height(45.dp)
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
                        ),
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                    navigationIcon = {},
                    actions = {},
                    title = {
                        ImageLogo(
                            tint = Color.Black,
                            painter = painterResource(Res.drawable.logotitle),
                            size = 60.dp
                        )
                    }
                )
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = ScreenBottom.Closet.route,
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(hazeState)
                //.padding(padding)
            ) {
                composable(ScreenBottom.Newspaper.route) { NewsletterScreen() }
                composable(ScreenBottom.Closet.route) { ClosetScreen() }
                composable(ScreenBottom.Profile.route) { ProfileScreen() }
            }
        }
    }
}

@Composable
fun ImageLogo(
    painter: Painter = painterResource(Res.drawable.logo),
    tint: Color,
    size: Dp = 55.dp
) {
    Icon(
        painter = painter,
        contentDescription = "Logo de Logo Circular Luxury Closet",
        modifier = Modifier.size(size),
        tint = tint
    )
}

@Composable
fun ClippyLogo(
    hazeState: HazeState,
    hazeStyle: HazeStyle
){
    Box(
        modifier = Modifier.graphicsLayer {
            translationX = 4.4.dp.toPx()
            translationY = 12.dp.toPx()
        }
    ){
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(45.dp)
                .background(Color.Transparent)
                .hazeEffect(state = hazeState, style = hazeStyle)
                .clickable { /* POR IMPLEMENTAR */ },
            contentAlignment = Alignment.Center
        ) {
            ImageLogo(tint = Color.White, size = 24.dp)
        }
    }
}