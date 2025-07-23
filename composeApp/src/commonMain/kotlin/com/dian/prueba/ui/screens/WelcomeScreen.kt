package com.dian.prueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
// SIN USO
@Composable
fun WelcomeScreen (name: String, navController: NavHostController) {
    Column (
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("Welcome, $name! :D", fontSize = 24.sp, textAlign = TextAlign.Center)
        Button(
            onClick = {
                navController.navigate("brand")
            }

        ){
            Text("Go to the app")
        }
    }
}