package com.dian.prueba.ui.screens.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dian.prueba.ui.components.WebViewZonaLogo

// todo: añadir zona logo (de momento login de bottega veneta)

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
    ) {
        WebViewZonaLogo(
            modifier = Modifier.fillMaxSize()
        )
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
