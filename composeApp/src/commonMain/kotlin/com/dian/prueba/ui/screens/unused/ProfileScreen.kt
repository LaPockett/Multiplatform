package com.dian.prueba.ui.screens.unused

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dian.prueba.liquidglass.components.navigation.Screen
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
                .padding(top = 20.dp, start = 320.dp)
                .zIndex(1f),
            containerColor = MaterialTheme.colorScheme.tertiary,

            ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onTertiary
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
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 60.dp)
        ) {
            Text(text = "Cerrar sesión", style = MaterialTheme.typography.bodyMedium)
        }

    }
}




