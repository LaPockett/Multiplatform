package com.dian.prueba.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.matcha
import multiplatform.composeapp.generated.resources.ysl
import org.jetbrains.compose.resources.painterResource

/**
 * Para ver su comportamiento junto con el bottom navigation
 */
@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
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
fun NewsletterScreen() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            items(22){
                Image(
                    painter = painterResource(Res.drawable.ysl),
                    contentDescription = "Newspaper",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

    }
}