package com.dian.prueba
import com.dian.prueba.utilities.TokenStorage
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.dian.prueba.HeaderManager.WebViewHeaderManager
import com.dian.prueba.navigation.BottomNavigationBar
import com.dian.prueba.navigation.Screen
import com.dian.prueba.network.generarToken
import com.dian.prueba.ui.components.MenuDrawer
import com.dian.prueba.ui.components.TopAppBarScreen
import com.dian.prueba.ui.screens.BrandScreen
import com.dian.prueba.ui.screens.WelcomeScreen
import com.dian.prueba.ui.screens.CartScreen
import com.dian.prueba.ui.screens.HomeScreen
import com.dian.prueba.ui.screens.LoginScreen
import com.dian.prueba.ui.screens.ProfileScreen
import com.dian.prueba.ui.screens.SearchScreen
import com.dian.prueba.utilities.Logger
import com.dian.prueba.ui.components.dialogs.UpdateAlertDialog
import com.dian.prueba.viewModel.UpdateVM
import com.russhwolf.settings.Settings

//expo react native
// cpu bench
// mirar hot reload en Android Studio

/**
 * NO SE USA
 */
val logger = Logger()
@Composable
fun App() {
    MaterialTheme {
        val title by remember { mutableStateOf("Welcome to multiplatform") }
        var textFieldName by remember { mutableStateOf("") }
        val navController: NavHostController = rememberNavController()

        NavHost(
            navController=navController,
            startDestination = "main"
        ) {
            composable(route = "main") {
                Column (
                    modifier = Modifier.padding(20.dp)
                ){
                    Row(
                        modifier = Modifier.padding(start = 20.dp, top = 10.dp)
                    ){
                        TextField(
                            textFieldName,
                            onValueChange = { textFieldName = it },
                            label = { Text("Insert your name") }
                        )

                    }
                    Button(
                        modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                        onClick = {
                            if (textFieldName.isNotBlank()) {
                                navController.navigate("welcome/$textFieldName")
                            }
                        }
                    ){
                        Text("Next activity")
                    }
                }
            }
            composable(route = "welcome/{name}") { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: ""
                WelcomeScreen(name = name, navController = navController)
            }
            composable(route = "brand") {
                BrandScreen()
            }

        }

    }
}

@Composable
fun AppLogin(){
    // Para ver en el Logcat que se están generando los tokens
    generarToken("access")
    generarToken("refresh")
    val settings = Settings()
    if (settings.getStringOrNull("refresh_token") == null){
        logger.debug(settings.getStringOrNull("refresh_token").toString(), "AppLogin")
        LoginScreen()

    } else {
        logger.warn("El token no es nulo", "AppLogin")
        logger.debug(settings.getStringOrNull("access_token").toString(), "AppLogin ACCESS TOKEN")
        logger.debug(settings.getStringOrNull("refresh_token").toString(), "AppLogin REfresh token")
        TokenStorage.loadTokens()
        logger.debug(TokenStorage.loadTokens().toString(), "AppLogin TokenStorage")
        WebViewHeaderManager.updateRefreshToken(TokenStorage.loadTokens()!!.refreshToken!!)
        WebViewHeaderManager.updateAccessToken(TokenStorage.loadTokens()!!.accessToken)
        logger.debug(WebViewHeaderManager.getHeaders().toString(), "AppLogin WebViewHeaderManager")
        logger.warn("Ingresando a MenuDrawer", "MenuDrawer")
        MenuDrawer()
    }
}

/**
 * APP AMAZON
 */

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val showBars = currentRoute != Screen.Profile.route
    val updateVM = UpdateVM()

    LaunchedEffect(Unit){
        logger.warn("Checking for updates...", "AppNavigation")
        updateVM.checkForUpdates()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        /*topBar = {
                TopAppBarScreen() // He agregado un topBAr aquí para poder habilitar luego el drawer
        },*/
        bottomBar = {
            // Para que el bottomBar no salga en la pantalla de Profile (Account), pero
            // sí en las demás pantallas
            if (showBars) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        val graph =
            navController.createGraph(startDestination = Screen.Home.route) {
                composable(route = Screen.Cart.route) {
                    CartScreen()
                }
                composable(route = Screen.Explore.route) {
                    SearchScreen()
                }
                composable(route = Screen.Home.route) {
                    HomeScreen()
                }
                composable(route = Screen.Profile.route) {
                    ProfileScreen(navController)
                }

            }
        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )
    }
    if (updateVM.showUpdateDialog.collectAsState().value){
        UpdateAlertDialog(viewModel = updateVM)
    }
}



