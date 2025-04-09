package com.dian.prueba.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*

@Composable
fun ProfileScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Profile Screen",
            modifier = Modifier.align(Alignment.Center)
        )

    }
}

