package com.dian.prueba.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dian.prueba.ui.screens.ClosetScreen
import com.dian.prueba.ui.screens.NewsletterScreen
import com.dian.prueba.ui.screens.ProfileScreen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Source: https://www.sinasamaki.com/glassmorphic-bottom-navigation-in-jetpack-compose/
 * Author: Sinasamaki
 */
@Preview
@Composable
fun MainNavigationPrueba() {
    val hazeState = remember { HazeState() }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            GlassmorphicBottomNavigation(hazeState, navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = ScreenBottom.Closet.route,
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState)
            //.padding(padding)
        ) {
            composable(ScreenBottom.Closet.route) { ClosetScreen() }
            composable(ScreenBottom.Newspaper.route) { NewsletterScreen() }
            composable(ScreenBottom.Profile.route) { ProfileScreen() }
        }
    }
}

@Composable
fun GlassmorphicBottomNavigation(hazeState: HazeState, navController: NavController) {
    var selectedTabIndex by remember { mutableIntStateOf(1) }
    val lightAlpha = 0.3f
    val darkAlpha = 0.1f
    val hazeStyle = HazeStyle(
        backgroundColor = Color.Transparent,
        tints = listOf(
            HazeTint(
                Color.Transparent.copy(alpha = if (Color.Transparent.luminance() >= 0.5) lightAlpha else darkAlpha),
            )
        ),
        blurRadius = 6.dp,
        noiseFactor = -1f,
        fallbackTint = HazeTint.Unspecified,
    )
    // Detectar destino actual
    val currentDestination = navController.currentBackStackEntry?.destination?.route
    selectedTabIndex = when (currentDestination) {
        ScreenBottom.Newspaper.route -> 0
        ScreenBottom.Closet.route -> 1
        ScreenBottom.Profile.route -> 2
        else -> selectedTabIndex
    }
    Box(
        modifier = Modifier
            .padding(horizontal = 64.dp).padding(bottom = 22.dp)
            .fillMaxWidth()
            .height(64.dp)
            .clip(CircleShape)
            .hazeEffect(state = hazeState, style = hazeStyle)
            .border(
                width = Dp.Hairline,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = .8f),
                        Color.White.copy(alpha = .2f),
                    ),
                ),
                shape = CircleShape
            )
            .background(Color.White.copy(alpha = 0.05f))
    ) {
        BottomBarTabs(
            tabs = tabs,
            selectedTab = selectedTabIndex,
            onTabSelected = { tab ->
                val index = tabs.indexOf(tab)
                selectedTabIndex = index
                // Navegación según tab
                when (tab) {
                    is BottomBarTab.Newspaper -> navController.navigate(ScreenBottom.Newspaper.route)
                    is BottomBarTab.Closet -> navController.navigate(ScreenBottom.Closet.route)
                    is BottomBarTab.Profile -> navController.navigate(ScreenBottom.Profile.route)
                }
            },
        )

        // Efectos de color y blur animados
        val animatedSelectedTabIndex by animateFloatAsState(
            targetValue = selectedTabIndex.toFloat(),
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )

        val animatedColor by animateColorAsState(
            targetValue = tabs[selectedTabIndex].color,
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .blur(35.dp, BlurredEdgeTreatment.Unbounded)
        ) {
            val tabWidth = size.width / tabs.size
            drawCircle(
                color = animatedColor.copy(alpha = .6f),
                radius = size.height / 2,
                center = Offset(
                    (tabWidth * animatedSelectedTabIndex) + tabWidth / 2,
                    size.height / 2
                )
            )
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        ) {
            val path = Path().apply {
                addRoundRect(RoundRect(size.toRect(), CornerRadius(size.height)))
            }
            val length = PathMeasure().apply { setPath(path, false) }.length

            val tabWidth = size.width / tabs.size
            drawPath(
                path = path,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        animatedColor.copy(alpha = 0f),
                        animatedColor.copy(alpha = 1f),
                        animatedColor.copy(alpha = 1f),
                        animatedColor.copy(alpha = 0f),
                    ),
                    startX = tabWidth * animatedSelectedTabIndex,
                    endX = tabWidth * (animatedSelectedTabIndex + 1),
                ),
                style = Stroke(
                    width = 6f,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(length / 2, length)
                    )
                )
            )
        }
    }
}

@Composable
fun BottomBarTabs(
    tabs: List<BottomBarTab>,
    selectedTab: Int,
    onTabSelected: (BottomBarTab) -> Unit,
) {
    CompositionLocalProvider(
        LocalTextStyle provides LocalTextStyle.current.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
        ),
        LocalContentColor provides Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            for (tab in tabs) {
                val alpha by animateFloatAsState(
                    targetValue = if (selectedTab == tabs.indexOf(tab)) 1f else .35f,
                    label = "alpha"
                )
                val scale by animateFloatAsState(
                    targetValue = if (selectedTab == tabs.indexOf(tab)) 1f else .98f,
                    visibilityThreshold = .000001f,
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                    ),
                    label = "scale"
                )
                val isSelected = selectedTab == tabs.indexOf(tab)
                Column(
                    modifier = Modifier
                        .scale(scale)
                        .alpha(alpha)
                        .fillMaxHeight()
                        .weight(1f)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                onTabSelected(tab)
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    // Para que solo salga el title si se ha seleccionado el tab
                    if (isSelected){
                        Icon(imageVector = tab.icon, contentDescription = "tab ${tab.title}")
                        Text(text = tab.title)
                    } else {
                        Icon(imageVector = tab.icon, contentDescription = "tab ${tab.title}")
                    }
                }
            }
        }
    }
}

sealed class BottomBarTab(val title: String, val icon: ImageVector, val color: Color) {
    data object Newspaper : BottomBarTab(
        title = "Newsletter",
        icon = Icons.Rounded.Newspaper,
        color = Color.White
    )
    data object Closet : BottomBarTab(
        title = "Closet",
        icon = Icons.Rounded.Notifications,
        color = Color.White
    )
    data object Profile : BottomBarTab(
        title = "Profile",
        icon = Icons.Rounded.Person,
        color = Color.White
    )
}

val tabs = listOf(
    BottomBarTab.Newspaper,
    BottomBarTab.Closet,
    BottomBarTab.Profile,
)

sealed class ScreenBottom(val route: String) {
    data object Newspaper : Screen("newsletter")
    data object Closet : Screen("closet")
    data object Profile : Screen("profile")
}