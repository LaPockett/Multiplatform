package com.dian.prueba.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import com.dian.prueba.ui.WebViewSearch

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