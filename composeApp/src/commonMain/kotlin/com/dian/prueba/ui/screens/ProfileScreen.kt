package com.dian.prueba.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dian.prueba.network.APIClient
import com.dian.prueba.repository.LoginRepositoryImpl
import com.dian.prueba.ui.components.WebViewAccount
import com.dian.prueba.utilities.TokenStorageImpl
import com.dian.prueba.utilities.UpdateStorageImpl
import com.dian.prueba.viewModel.LoginVM
import com.russhwolf.settings.Settings

@Composable
fun ProfileScreen(navController: NavController){
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
    ){
        FloatingActionButton(
            onClick = {
                navController.navigate("home")
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 110.dp, start = 20.dp)
                .zIndex(1f),
            backgroundColor = Color(0xFF080e45),

            ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
        WebViewAccount(
            modifier = Modifier.fillMaxSize(),
        )
        Button(
            onClick = {
                loginViewModel.clearSavedTokens()
                // Arreglar esto para que el usuario no pueda salir del Login y para que
                // no salgan los elementos del bottomNavigation
                navController.navigate(route= "login")
            },
            colors = ButtonDefaults.buttonColors(
                // Color crema #b7af98
                backgroundColor = Color.Red,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom =60.dp)
        ) {
            Text("Cerrar sesión")
        }
    }
}

