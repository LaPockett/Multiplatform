package com.dian.prueba.ui.screens.unused

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dian.prueba.utilities.Logger

/**
 * NONE OF THESE COMPOSABLES ARE USED IN THE APP, but I'm leaving them here in case I need them in the future
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarScreen4() {
    val logger = Logger("TopAppBarScreen2")
    logger.debug("Enseñando el TopAppBar...")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {
                    Text(text = "Shopping Cart- Amazon")
                },
                navigationIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                }
            )
            //CartScreen(modifier)
        }

    }

}
