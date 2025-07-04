package com.dian.prueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.dian.prueba.ui.components.WebViewCart

@Composable
fun CartScreen(){

    Box(
        modifier = Modifier.fillMaxSize().padding(top = 100.dp)
    ){
        WebViewCart(
            modifier = Modifier.fillMaxWidth()
        )
    }
}