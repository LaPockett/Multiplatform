package com.dian.prueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import com.dian.prueba.ui.components.WebViewCart

@Composable
fun CartScreen(){

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        WebViewCart(
            modifier = Modifier.fillMaxWidth()
        )
    }
}