package com.dian.prueba.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dian.prueba.viewModel.LoginVM
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import com.dian.prueba.AppNavigation
import com.dian.prueba.utilities.Logger
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import com.dian.prueba.HeaderManager.WebViewHeaderManager
import com.dian.prueba.network.APIClient
import com.dian.prueba.repository.LoginRepositoryImpl
import com.dian.prueba.ui.components.MenuDrawer
import com.dian.prueba.ui.components.dialogs.showAlertDialogLogin
import com.dian.prueba.utilities.TokenStorage

@Composable
fun LoginScreen(){
    val loginViewModel = LoginVM(
        loginRepository = LoginRepositoryImpl(
            apiClient = APIClient(),
            logger = Logger()
        )
    )
    // No se hará nada con el email del usuario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val logger = Logger()
    val navController: NavHostController = rememberNavController()
    var showDialog by remember {mutableStateOf(false)}
    NavHost(
        navController = navController,
        startDestination = "login"
    ){
        composable(route = "login"){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    OutlinedTextField(
                        value = email,
                        onValueChange = {email = it},
                        label = { Text("Email") }
                    )
                    Spacer(
                        modifier = Modifier.padding(16.dp)
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = {password = it},
                        label = {Text("Contraseña")}
                    )
                    Spacer(
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(
                        onClick = {

                            /**
                             * Ahora lo que hago es que desde el viewModel se genere el accessToken que son
                             * 10 caracteres de letras aleatorias y se lo paso al WebViewHeaderManager
                             * Esto está mal pero la LoginCookie es la contraseña del usuario
                             * EL refresh_token es el email del usuario
                             * Cada vez que se haga login (desde el button) se va a generar un nuevo accessToken
                            */
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                logger.warn("WARN - El usuario ha hecho click en login", "LoginScreen")

                                loginViewModel.loadSavedTokens() //!!

                                loginViewModel.loginUser(5) // Lucio_Hettinger@annie.ca
                                loginViewModel.tokens.value?.accessToken?.let { tokens ->
                                    WebViewHeaderManager.updateAccessToken(tokens)
                                    WebViewHeaderManager.updateLoginCookie(password)
                                }
                                loginViewModel.tokens.value?.refreshToken?.let { tokens ->
                                    WebViewHeaderManager.updateRefreshToken(tokens)
                                }
                                //TokenStorage.saveTokens(loginViewModel.tokens.value!!)

                                logger.debug(WebViewHeaderManager.getHeaders().toString(), "LoginScreen WebViewHeaderManager")

                                navController.navigate("MenuDrawer")

                            } else {
                                showDialog = true
                            }
                        },
                        enabled = true
                    ){
                        Text("Iniciar sesión")
                    }
                    Spacer(
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            if (showDialog){
                showAlertDialogLogin(
                    texto = "Debes introducir un email y una contraseña",
                    onDismissRequest = {showDialog = false}
                )
            }

        }
        composable(route = "MenuDrawer"){
            MenuDrawer()
        }
    }
}

