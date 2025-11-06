package com.dian.prueba.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dian.prueba.model.LocalPadding
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.flower
import multiplatform.composeapp.generated.resources.matcha
import org.jetbrains.compose.resources.painterResource

/**
 * Para ver su comportamiento junto con el bottom navigation
 */
@Composable
fun ProfileScreen(
    paddingValues: PaddingValues
) {
    val paddingModifier = LocalPadding.current
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = paddingModifier.tiny)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(paddingModifier.spacedBy),
            verticalArrangement = Arrangement.spacedBy(paddingModifier.spacedBy),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(22){
                Image(
                    painter = painterResource(Res.drawable.matcha),
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

    }
}

@Composable
fun NewsletterScreen(
    paddingValues: PaddingValues
) {
    val paddingModifier = LocalPadding.current
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = paddingModifier.tiny)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(paddingModifier.spacedBy),
            verticalArrangement = Arrangement.spacedBy(paddingModifier.spacedBy),
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