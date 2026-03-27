package com.dian.prueba.ui.screens.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.dian.prueba.data.globalResources.LocalColors
import com.dian.prueba.data.globalResources.LocalPadding
import com.dian.prueba.ui.components.WebViewZonaLogo
import com.kyant.backdrop.backdrops.layerBackdrop

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    val paddingModifier = LocalPadding.current
    val colorModifier = LocalColors.current

    /* *
    * Incluyo LazyColumn solo para poder utilizar el contentPadding con paddingValues y que el webview
    * esté por debajo del bottom navigation, pero cuando llegue al final de la web no quede
    * por debajo y así el usuario pueda ver todos los elementos correctamente.
    */

    AppScaffold(
        navController = navController,
    ) { paddingValues, backdrop ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop)
                .background(colorModifier.backgroundApp)
                .padding(horizontal = paddingModifier.tiny)
        ) {
            item {
                WebViewZonaLogo(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

/* *
 * Para ver su comportamiento junto con el bottom navigation
 */
/*@Composable
fun ProfileScreen(
    paddingValues: PaddingValues
) {
    val paddingModifier = LocalPadding.current
    val colorModifier = LocalColors.current
    Box(
        modifier = Modifier
            .background(colorModifier.backgroundApp)
            .padding(horizontal = paddingModifier.tiny)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(paddingModifier.extraTiny),
            verticalArrangement = Arrangement.spacedBy(paddingModifier.extraTiny),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(22){
                Image(
                    painter = painterResource(Res.drawable.matcha),
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize()
                )
                /*AsyncImage(
                    model = "https://images.pexels.com/photos/979003/pexels-photo-979003.jpeg",
                    contentDescription = "ImageExample",
                    modifier = Modifier.fillMaxSize()
                )*/
            }
        }

    }
}*/
