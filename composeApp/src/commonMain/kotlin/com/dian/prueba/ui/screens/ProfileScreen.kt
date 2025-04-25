package com.dian.prueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dian.prueba.ui.components.WebViewAccount

@Composable
fun ProfileScreen(navController: NavController){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        WebViewAccount(
            modifier = Modifier.fillMaxSize(),
        )
        FloatingActionButton(
            onClick = {
                navController.navigate("home")
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            backgroundColor = Color(0xFF080e45),

            ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
    }
}

