package com.dian.prueba.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.dian.prueba.ui.screens.CartScreen
import com.dian.prueba.utilities.Logger

// Comando para formatear el código en Android Studio (Linux): Ctrl + Shift + Alt + L
/**
 * NINGUNO DE ESTOS COMPOSABLES SE USA EN LA APP, pero los dejo aquí por si los llego
 * a necesitar en el futuro
 */

/**
 * EN USO
 */
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
            CartScreen()
        }

    }

}
