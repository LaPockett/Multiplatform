package com.dian.prueba.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dian.prueba.viewModel.LoginVM
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.*
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dian.prueba.HeaderManager.WebViewHeaderManager
import com.dian.prueba.navigation.Screen
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
/**
 * THe following two lines appear in red from time to time. It's not a error,
 * it's just a bug. You just need to clean and build project or forget about it :)
 */
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.amazon_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(navController: NavHostController){
    val focusManager = LocalFocusManager.current
    MaterialTheme{
        val currentBackStackEntry = navController.currentBackStackEntryAsState()

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
        // Nothing will be done with the user's credentials
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val logger = Logger("LoginScreen")
        var showDialog by remember {mutableStateOf(false)}
        var showDialogInvalidData by remember {mutableStateOf(false)}
        LaunchedEffect(currentBackStackEntry.value) {
            email = ""
            password = ""
        }
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route
        ){
            composable(route = Screen.Login.route){
                Box(
                    modifier = Modifier.fillMaxSize()
                        .pointerInput(Unit){detectTapGestures(onTap = {focusManager.clearFocus()})},
                    contentAlignment = Alignment.Center
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        // Instead of just typing 'R', you type 'Res'
                        Image(
                            painter = painterResource(Res.drawable.amazon_logo),
                            contentDescription = "Logo de Amazon",
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
                            /**
                             * It's recommended to specify the keyboard type:
                             * - In this case it shows you the emails that exist on your device (apparently only in Android)
                             */
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF626D8B),
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
                                unfocusedBorderColor = Color(0xFF626D8B),
                                focusedBorderColor = Color(0xFFf7f4f0)
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                            /**
                             * It's recommended to specify the keyboard type:
                             * - In this case it doesn't show the password that the user writes
                             */
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
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

                                        logger.debug(WebViewHeaderManager.getHeaders().toString())
                                        logger.warn("El usuario ha hecho click en login")
                                        navController.navigate("MenuDrawer") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }
                                    Resultado.Empty -> showDialog = true
                                    Resultado.Invalid -> showDialogInvalidData = true
                                }
                            },
                            enabled = true,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF626D8B),
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
                MenuDrawer(onLogout = {
                    navController.navigate(Screen.Login.route){
                        popUpTo(0){inclusive = true}
                    }
                })
            }
        }
    }
}

