package com.dian.prueba.ui.screens.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dian.prueba.data.globalResources.LocalColors
import com.dian.prueba.data.globalResources.LocalPadding
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.flower
import org.jetbrains.compose.resources.painterResource

//todo: Renderizador de Markdown que se mostrará en esta screen
@Composable
fun NewsletterScreen(
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
                    painter = painterResource(Res.drawable.flower),
                    contentDescription = "Newspaper",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}