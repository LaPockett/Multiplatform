package com.dian.prueba.ui.screens.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dian.prueba.data.globalResources.LocalColors
import com.dian.prueba.data.globalResources.LocalDimension
import com.dian.prueba.data.globalResources.LocalPadding
import com.dian.prueba.liquidglass.destinations.BottomTabsLiquidGlass
import com.dian.prueba.liquidglass.destinations.GlassClippyLogo
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownImage
import com.mikepenz.markdown.compose.elements.MarkdownParagraph
import com.mikepenz.markdown.coil3.Coil3ImageTransformerImpl
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.rememberMarkdownState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.logo
import multiplatform.composeapp.generated.resources.logotitle
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsletterScreen(
    navController: NavHostController
) {
    val colorModifier = LocalColors.current
    val paddingModifier = LocalPadding.current

    val components = markdownComponents(
        // !error: Image is animating to actual size on Android...
        // ref: https://github.com/mikepenz/multiplatform-markdown-renderer/issues/430
        image = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MarkdownImage(it.content, it.node)
                }
            }
        },
        paragraph = {
            Column {
                MarkdownParagraph(it.content, it.node)
                Spacer(
                    Modifier.padding(vertical = paddingModifier.extraTiny)
                )
            }
        },
    )

    val uiDimensions = LocalDimension.current
    val hazeState = rememberHazeState()
    val lightAlpha = 0.3f
    val darkAlpha = 0.1f
    val backdrop = rememberLayerBackdrop()

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
                            offset = DpOffset(x = 4.dp, 4.dp)
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

        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop)
                .background(colorModifier.backgroundApp)
                .padding(horizontal = paddingModifier.tiny),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
                    .padding(
                        top = paddingValues.calculateTopPadding() + paddingModifier.tiny,
                        bottom = paddingValues.calculateBottomPadding()
                    ),
            ) {
                Markdown(
                    markdownState = rememberMarkdownState {
                        Res.readBytes("files/example.md").decodeToString()
                    },
                    components = components,
                    typography = markdownTypography(
                        h1 = MaterialTheme.typography.titleLarge.copy(
                            color = colorModifier.logoColor
                        ),
                        h2 = MaterialTheme.typography.titleMedium.copy(
                            color = colorModifier.logoColor
                        ),
                        h3 = MaterialTheme.typography.titleSmall.copy(
                            color = colorModifier.logoColor
                        ),
                        text = MaterialTheme.typography.bodyMedium.copy(
                            color = colorModifier.blackLight
                        ),
                        quote = MaterialTheme.typography.bodySmall.copy(
                            color = colorModifier.logoColor,
                            fontStyle = FontStyle.Italic
                        ),
                        paragraph = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Black,
                            textAlign = TextAlign.Justify
                        ),
                        textLink = TextLinkStyles(
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.Blue
                            ).toSpanStyle()
                        ),
                        ordered = MaterialTheme.typography.titleSmall.copy(
                            color = Color.Blue
                        )
                    ),
                    imageTransformer = Coil3ImageTransformerImpl,
                )
            }
        }
    }
}

@Composable
private fun Header() {
    val colorModifier = LocalColors.current
    val paddingModifier = LocalPadding.current
    Column {
        Text(
            "Luxury Newsletter",
            style = MaterialTheme.typography.titleLarge,
            color = colorModifier.logoColor,
            letterSpacing = 0.4.sp,
            modifier = Modifier.padding(bottom = paddingModifier.extraTiny)
        )
        Spacer(Modifier.height(paddingModifier.tiny))
    }
}