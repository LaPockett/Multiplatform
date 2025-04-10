package com.dian.prueba.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import com.dian.prueba.ui.WebViewCart

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