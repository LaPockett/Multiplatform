package com.dian.prueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dian.prueba.navigation.Screen
import com.dian.prueba.network.APIClient
import com.dian.prueba.repository.LoginRepositoryImpl
import com.dian.prueba.ui.components.WebViewAccount
import com.dian.prueba.utilities.TokenStorageImpl
import com.dian.prueba.utilities.UpdateStorageImpl
import com.dian.prueba.viewModel.LoginVM
import com.russhwolf.settings.Settings

@Composable
fun ProfileScreen(navController: NavController, onLogout: () -> Unit) {
    val loginViewModel = remember {
        LoginVM(
            loginRepository = LoginRepositoryImpl(
                apiService = APIClient(
                    updateStorage = UpdateStorageImpl(
                        settings = Settings()
                    )
                )
            ),
            tokenStorage = TokenStorageImpl(
                settings = Settings()
            )
        )
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.Home.route)
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 110.dp, start = 20.dp)
                .zIndex(1f),
            backgroundColor = Color(0xFF080e45),

            ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        WebViewAccount(
            modifier = Modifier.fillMaxSize(),
        )
        Button(
            onClick = {
                loginViewModel.clearSavedTokens()
                onLogout()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 60.dp)
        ) {
            Text("Cerrar sesión")
        }
    }
}




