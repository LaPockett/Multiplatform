package com.dian.prueba.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dian.prueba.navigation.BottomNavigationBarLogo
import com.dian.prueba.navigation.ScreenBottom
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.dian.prueba.ui.components.WebViewLogoCloset
import com.dian.prueba.ui.components.WebViewLogoNewsletter
import com.dian.prueba.ui.components.WebViewLogoProfile
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.rememberHazeState
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.logotitle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Preview
@Composable
fun FeedLogoWebView() {
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
            modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing),
            bottomBar = {
                BottomNavigationBarLogo(navController, hazeState)
            }/*,
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.fillMaxWidth().padding(WindowInsets.safeDrawing.asPaddingValues())
                        .height(45.dp)
                        .hazeEffect(
                            state = hazeState,
                            style = hazeStyle,
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.White, Color.Transparent),
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
            }*/
        ) { innerPadding -> // Padding values para el contenido, podemos elegir el que queramos
            NavHost(
                navController = navController,
                startDestination = ScreenBottom.Closet.route,
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(hazeState)
                    //.padding(top = innerPadding.calculateTopPadding())
                //.padding(padding)
            ) {
                composable(ScreenBottom.Newspaper.route) { NewspaperScreenWebView() }
                composable(ScreenBottom.Closet.route) { ClosetScreenWebview() }
                composable(ScreenBottom.Profile.route) { ProfileScreenWebView() }
            }
        }
    }
}
@Composable
fun ClosetScreenWebview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            //.padding(horizontal = 12.dp)
    ) {
        WebViewLogoCloset()
    }
}

@Composable
fun NewspaperScreenWebView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            //.padding(horizontal = 12.dp)
    ) {
        WebViewLogoNewsletter()
    }
}

@Composable
fun ProfileScreenWebView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            //.padding(horizontal = 12.dp)
    ) {
        WebViewLogoProfile()
    }
}

