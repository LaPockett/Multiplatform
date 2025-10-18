package com.dian.prueba.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.dian.prueba.navigation.BottomNavigationBarLogo
import com.dian.prueba.navigation.NewspaperScreen
import com.dian.prueba.navigation.ProfileScreen
import com.dian.prueba.navigation.ScreenBottom
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.dian.prueba.ui.components.SearchBar
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.rememberHazeState
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HeaderLogo() {
    MultiplatformTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
                .padding(top = 50.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                /*Box(
                    modifier = Modifier.height(130.dp)
                        .fillMaxSize()
                        .border(3.dp, Color(0xf0dcb57f)).align(Alignment.BottomCenter),
                )*/
                AsyncImage(
                    model = Res.getUri("files/border.svg"),
                    contentDescription = "Border SVG",
                    modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter),
                )
                AsyncImage(
                    model = Res.getUri("files/banner.plain.svg"),
                    contentDescription = "Banner SVG",
                    modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter),
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Your daily inspiration",
                    style = MaterialTheme.typography.bodyLarge,
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
fun FeedLogo() {
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
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { /* POR IMPLEMENTAR */ }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .hazeEffect(state = hazeState, style = hazeStyle),
                        contentAlignment = Alignment.Center
                    ) {
                        ImageLogo(tint = Color.White, size = 25.dp)
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigationBarLogo(navController, hazeState)
            },
            /*topBar = {
            }*/
        ) {
            NavHost(
                navController = navController,
                startDestination = ScreenBottom.Closet.route,
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(hazeState)
                //.padding(padding)
            ) {
                composable(ScreenBottom.Newspaper.route) { NewspaperScreen() }
                composable(ScreenBottom.Closet.route) { ClosetScreen() }
                composable(ScreenBottom.Profile.route) { ProfileScreen() }
            }
        }
    }
}

@Composable
fun ImageLogo(
    painter : Painter = painterResource(Res.drawable.logo),
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