package com.dian.prueba.ui.screens.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dian.prueba.data.globalResources.LocalColors
import com.dian.prueba.data.globalResources.LocalDimension
import com.dian.prueba.liquidglass.destinations.BottomTabsLiquidGlass
import com.dian.prueba.liquidglass.destinations.GlassClippyLogo
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.rememberHazeState
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.logo
import multiplatform.composeapp.generated.resources.logotitle
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun AppScaffold(
    navController: NavHostController,
    content: @Composable (PaddingValues, LayerBackdrop) -> Unit
) {
    val colorModifier = LocalColors.current
    val uiDimensions = LocalDimension.current
    val hazeState = rememberHazeState()
    val backdrop = rememberLayerBackdrop()

    val hazeStyle = HazeStyle(
        backgroundColor = Color.White,
        tints = listOf(
            HazeTint(
                Color.White.copy(alpha = if (Color.White.luminance() >= 0.5f) 0.3f else 0.1f)
            )
        ),
        blurRadius = 5.dp,
        noiseFactor = -1f,
        fallbackTint = HazeTint.Unspecified,
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                            offset = DpOffset(x = 4.dp, y = 4.dp)
                        )
                    )
                    .height(95.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorModifier.backgroundApp,
                                colorModifier.backgroundApp.copy(alpha = 0.9f),
                                colorModifier.backgroundApp.copy(alpha = 1f),
                                colorModifier.backgroundApp.copy(alpha = 0.8f),
                                colorModifier.backgroundApp.copy(alpha = 0.7f),
                                colorModifier.backgroundApp.copy(alpha = 0.6f),
                                colorModifier.backgroundApp.copy(alpha = 0.5f),
                                colorModifier.backgroundApp.copy(alpha = 0.4f),
                                colorModifier.backgroundApp.copy(alpha = 0.3f),
                                colorModifier.backgroundApp.copy(alpha = 0.1f),
                                colorModifier.backgroundApp.copy(alpha = 0f),
                            ),
                            startY = 245f,
                            endY = 300f,
                        )
                    ),
                windowInsets = WindowInsets.statusBars,
                navigationIcon = {},
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
        contentWindowInsets = WindowInsets.statusBars,
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
            Box(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
                BottomTabsLiquidGlass(backdrop, navController)
            }
        },
    ) { paddingValues ->
        content(paddingValues, backdrop)
    }
}