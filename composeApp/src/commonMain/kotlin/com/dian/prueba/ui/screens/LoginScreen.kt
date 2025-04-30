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
import com.dian.prueba.ui.components.dialogs.showAlertDialogLogin
import com.dian.prueba.utilities.TokenStorage

@Composable
fun LoginScreen(viewModel: LoginVM = LoginVM()){
    val loginViewModel = LoginVM()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val token by viewModel.tokens.collectAsState()
    val logger = Logger()
    val navController: NavHostController = rememberNavController()
    //var stateButton by remember{ mutableStateOf(false) }
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

                            /** Cuando le daba click el token era nulo por lo que no nos llevaba al AppNavigation
                            * Cuando le vuelves a dar click por segunda vez sí nos lleva porque el token se carga
                            * Se ha cambiado lo del token, pero no creo que esté bien, así que lo tengo que revisar
                             * Le he dado un poco más de formato al Login con un email y password y si el
                             * usuario no pone algún dato le salta un AlertDialog, pero de momento no se
                             * hace nada con esos datos.
                            */
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                loginViewModel.loadSavedTokens()
                                WebViewHeaderManager.updateLoginCookie(password)
                                logger.debug(WebViewHeaderManager.getHeaders().toString(), "LoginScreen")
                                logger.warn("El usuario ha hecho click en login", "LoginScreen")
                                TokenStorage.saveTokens(loginViewModel.tokens.value!!)
                                logger.debug(loginViewModel.tokens.value.toString(), "LoginScreen access Token")
                                navController.navigate("AppNavigation")
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
        composable(route = "AppNavigation"){
            AppNavigation()
        }
    }
}

