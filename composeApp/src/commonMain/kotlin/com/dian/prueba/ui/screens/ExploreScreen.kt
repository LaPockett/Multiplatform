package com.dian.prueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.dian.prueba.ui.components.WebViewSearch

@Composable
fun SearchScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ){
        WebViewSearch(
            modifier = Modifier.fillMaxSize()
        )

    }
}