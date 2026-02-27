package com.dian.prueba.liquidglass.destinations

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dian.prueba.liquidglass.components.LiquidBottomTab
import com.dian.prueba.liquidglass.components.LiquidBottomTabs
import com.dian.prueba.liquidglass.BackdropDemoScaffold
import com.dian.prueba.liquidglass.Block
import com.dian.prueba.data.globalResources.LocalPadding
import com.dian.prueba.liquidglass.components.navigation.ScreenBottom
import com.dian.prueba.liquidglass.components.navigation.tabs
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.kyant.backdrop.backdrops.LayerBackdrop
import org.jetbrains.compose.resources.painterResource
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.flight_40px
import multiplatform.composeapp.generated.resources.system_home_screen_light

@Composable
fun BottomTabsContent() {
    val isLightTheme = !isSystemInDarkTheme()
    val contentColor = if (isLightTheme) Color.Black else Color.White

    val airplaneModeIcon = painterResource(Res.drawable.flight_40px)
    val iconColorFilter = ColorFilter.tint(contentColor)

    BackdropDemoScaffold(
        initialPainterResId = Res.drawable.system_home_screen_light
    ) { backdrop ->
        Column(verticalArrangement = Arrangement.spacedBy(32f.dp)) {
            Block {
                var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

                LiquidBottomTabs(
                    selectedTabIndex = { selectedTabIndex },
                    onTabSelected = { selectedTabIndex = it },
                    backdrop = backdrop,
                    tabsCount = 3,
                    modifier = Modifier.padding(horizontal = 36f.dp)
                ) {
                    repeat(3) { index ->
                        LiquidBottomTab({ selectedTabIndex = index }) {
                            Box(
                                Modifier
                                    .size(28f.dp)
                                    .paint(airplaneModeIcon, colorFilter = iconColorFilter)
                            )
                            BasicText(
                                "Tab ${index + 1}",
                                style = TextStyle(contentColor, 12f.sp)
                            )
                        }
                    }
                }
            }
            Block {
                var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

                LiquidBottomTabs(
                    selectedTabIndex = { selectedTabIndex },
                    onTabSelected = { selectedTabIndex = it },
                    backdrop = backdrop,
                    tabsCount = 4,
                    modifier = Modifier.padding(horizontal = 36f.dp)
                ) {
                    repeat(4) { index ->
                        LiquidBottomTab({ selectedTabIndex = index }) {
                            Box(
                                Modifier
                                    .size(28f.dp)
                                    .paint(airplaneModeIcon, colorFilter = iconColorFilter)
                            )
                            BasicText(
                                "Tab ${index + 1}",
                                style = TextStyle(contentColor, 12f.sp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomTabsLiquidGlass(
    backdrop: LayerBackdrop,
    navController: NavController
) {
    val paddingModifier = LocalPadding.current
    val isLightTheme = !isSystemInDarkTheme()
    val contentColor = if (isLightTheme) Color.LightGray else Color.White

    val iconColorFilter = ColorFilter.tint(contentColor)
    // Primer destino
    var selectedTabIndex by remember { mutableIntStateOf(1) }

    // Detectar destino actual
    val currentDestination = navController.currentBackStackEntry?.destination?.route
    selectedTabIndex = when (currentDestination) {
        ScreenBottom.Newspaper.route -> 0
        ScreenBottom.Closet.route -> 1
        ScreenBottom.Profile.route -> 2
        else -> selectedTabIndex
    }
    MultiplatformTheme {
        Column(verticalArrangement = Arrangement.spacedBy(paddingModifier.extraBig)) {
            Block {
                LiquidBottomTabs(
                    selectedTabIndex = { selectedTabIndex },
                    onTabSelected = { tab ->
                        selectedTabIndex = tab
                        when (tab) {
                            0 -> navController.navigate(ScreenBottom.Newspaper.route)
                            1 -> navController.navigate(ScreenBottom.Closet.route)
                            2 -> navController.navigate(ScreenBottom.Profile.route)
                        }
                    },
                    backdrop = backdrop,
                    tabsCount = 3,
                    modifier = Modifier.padding(horizontal = paddingModifier.extraLarge)
                ) {
                    tabs.forEachIndexed { index, tab ->
                        LiquidBottomTab({ selectedTabIndex = index }) {
                            Box (
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = tabs[index].icon,
                                        contentDescription = "Icon ${tabs[index].title}",
                                        tint = Color(0xffcccccc)
                                    )
                                    Text(
                                        text = tabs[index].title,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xffcccccc),
                                        modifier = Modifier.padding(paddingModifier.extraTiny)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}