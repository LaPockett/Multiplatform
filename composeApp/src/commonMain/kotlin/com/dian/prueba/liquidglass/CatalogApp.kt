package com.dian.prueba.liquidglass

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.Color
import com.dian.prueba.liquidglass.destinations.AdaptiveLuminanceGlassContent
import com.dian.prueba.liquidglass.destinations.BottomTabsContent
import com.dian.prueba.liquidglass.destinations.ButtonsContent
import com.dian.prueba.liquidglass.destinations.ControlCenterContent
import com.dian.prueba.liquidglass.destinations.DialogContent
import com.dian.prueba.liquidglass.destinations.GlassPlaygroundContent
import com.dian.prueba.liquidglass.destinations.HomeContent
import com.dian.prueba.liquidglass.destinations.LazyScrollContainerContent
import com.dian.prueba.liquidglass.destinations.MagnifierContent
import com.dian.prueba.liquidglass.destinations.ProgressiveBlurContent
import com.dian.prueba.liquidglass.destinations.ScrollContainerContent
import com.dian.prueba.liquidglass.destinations.SliderContent
import com.dian.prueba.liquidglass.destinations.ToggleContent

@Composable
fun CatalogApp() {
    val isLightTheme = !isSystemInDarkTheme()
    val backgroundColor = if (isLightTheme) Color.White else Color.Black

    CompositionLocalProvider(
        LocalIndication provides ripple(color = if (isLightTheme) Color.Black else Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            MainContent()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MainContent() {
    var destination by rememberSaveable { mutableStateOf(CatalogDestination.Home) }

    BackHandler(destination != CatalogDestination.Home) {
        destination = CatalogDestination.Home
    }

    when (destination) {
        CatalogDestination.Home -> HomeContent(onNavigate = { destination = it })

        CatalogDestination.Buttons -> ButtonsContent()
        CatalogDestination.Toggle -> ToggleContent()
        CatalogDestination.Slider -> SliderContent()
        CatalogDestination.BottomTabs -> BottomTabsContent()
        CatalogDestination.Dialog -> DialogContent()

        CatalogDestination.ControlCenter -> ControlCenterContent()
        CatalogDestination.Magnifier -> MagnifierContent()

        CatalogDestination.GlassPlayground -> GlassPlaygroundContent()
        CatalogDestination.AdaptiveLuminanceGlass -> AdaptiveLuminanceGlassContent()
        CatalogDestination.ProgressiveBlur -> ProgressiveBlurContent()
        CatalogDestination.ScrollContainer -> ScrollContainerContent()
        CatalogDestination.LazyScrollContainer -> LazyScrollContainerContent()
    }
}
