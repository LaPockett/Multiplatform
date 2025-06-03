package com.dian.prueba.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dian.prueba.viewModel.LoginVM
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import com.dian.prueba.utilities.Logger
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.*
import com.dian.prueba.HeaderManager.WebViewHeaderManager
import com.dian.prueba.network.APIClient
import com.dian.prueba.repository.LoginRepositoryImpl
import com.dian.prueba.ui.components.MenuDrawer
import com.dian.prueba.ui.components.dialogs.InvalidDataAlertDialogLogin
import com.dian.prueba.ui.components.dialogs.showAlertDialogLogin
import com.dian.prueba.utilities.LoginValidator
import com.dian.prueba.utilities.TokenStorageImpl
import com.dian.prueba.utilities.UpdateStorageImpl
import com.dian.prueba.utilities.Resultado
import com.russhwolf.settings.Settings
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(){
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
    // No se hará nada con el email del usuario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val logger = Logger("LoginScreen")
    val navController: NavHostController = rememberNavController()
    var showDialog by remember {mutableStateOf(false)}
    var showDialogInvalidData by remember {mutableStateOf(false)}
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
                    // En vez de solo poner 'R', se pone 'Res'
                    Image(
                        painter = painterResource(Res.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(100.dp).aspectRatio(1f)
                            .clip(MaterialTheme.shapes.medium).fillMaxWidth()
                    )
                    Spacer(
                        modifier = Modifier.padding(16.dp)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = {email = it},
                        label = { Text("Email") },
                        colors = OutlinedTextFieldDefaults.colors(
                            // Color crema más claro #f7f4f0
                            unfocusedBorderColor = Color(0xFFb7af98),
                            focusedBorderColor = Color(0xFFf7f4f0)
                        )
                    )
                    Spacer(
                        modifier = Modifier.padding(16.dp)
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = {password = it},
                        label = {Text("Contraseña")},
                        colors = OutlinedTextFieldDefaults.colors(
                            // Color crema más claro #f7f4f0
                            unfocusedBorderColor = Color(0xFFb7af98),
                            focusedBorderColor = Color(0xFFf7f4f0)
                        ),
                        visualTransformation = PasswordVisualTransformation()
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
                            when (LoginValidator.validateLogin(email, password)) {
                                Resultado.Valid -> {
                                    logger.warn("WARN - El usuario ha hecho click en login")

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

                                    logger.debug(WebViewHeaderManager.getHeaders().toString())
                                    logger.warn("El usuario ha hecho click en login")
                                    navController.navigate("MenuDrawer")
                                }
                                Resultado.Empty -> showDialog = true
                                Resultado.Invalid -> showDialogInvalidData = true
                            }
                        },
                        enabled = true,
                        colors = ButtonDefaults.buttonColors(
                            // Color crema #b7af98
                            backgroundColor = Color(0xFFb7af98),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),

                    ){
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Login",
                            modifier = Modifier.padding(end=6.dp)
                        )
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
            if (showDialogInvalidData){
                InvalidDataAlertDialogLogin(
                    onDismissRequest = {showDialogInvalidData = false}
                )
            }

        }
        composable(route = "MenuDrawer"){
            MenuDrawer()
        }
    }
}

