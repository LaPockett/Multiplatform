package com.dian.prueba.ui.screens.unused

import com.dian.prueba.utilities.TokenStorage
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.dian.prueba.HeaderManager.WebViewHeaderManager
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.dian.prueba.liquidglass.components.navigation.BottomNavigationBar
import com.dian.prueba.liquidglass.components.navigation.Screen
import com.dian.prueba.network.APIClient
import com.dian.prueba.ui.components.MenuDrawer
import com.dian.prueba.utilities.Logger
import com.dian.prueba.ui.components.dialogs.UpdateAlertDialog
import com.dian.prueba.utilities.TokenStorageImpl
import com.dian.prueba.utilities.UpdateStorageImpl
import com.dian.prueba.viewModel.UpdateVM
import com.russhwolf.settings.*

/**
 * NO SE USA
 */

/*@Composable
fun App() {
    MaterialTheme {
        val title by remember { mutableStateOf("Welcome to multiplatform") }
        var textFieldName by remember { mutableStateOf("") }
        val navController: NavHostController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "main"
        ) {
            composable(route = "main") {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(title)
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(
                        modifier = Modifier.padding(start = 20.dp, top = 10.dp)
                    ) {
                        TextField(
                            textFieldName,
                            onValueChange = { textFieldName = it },
                            label = { Text("Insert your name") }
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                        onClick = {
                            if (textFieldName.isNotBlank()) {
                                navController.navigate("welcome/$textFieldName")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color(0xffeaf7f8),
                            containerColor = Color(0xff7265da)
                        )
                    ) {
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
}*/
@Composable
fun AppLogin() {
    /**
     * You are first checked to see if you are logged in, using tokens to redirect you
     * to the login screen or the menu drawer that contains the main application.
     */
    MultiplatformTheme {
        val logger = Logger("AppLogin")
        val settings = Settings()
        val tokenStorage: TokenStorage = TokenStorageImpl(settings)
        val navController = rememberNavController()

        if (settings.getStringOrNull("refresh_token") == null) {
            logger.debug(settings.getStringOrNull("refresh_token").toString())
            LoginScreen(navController)

        } else {
            logger.warn("El token no es nulo")
            logger.debug(settings.getStringOrNull("access_token").toString())
            logger.debug(settings.getStringOrNull("refresh_token").toString())
            tokenStorage.loadTokens()
            logger.debug(tokenStorage.loadTokens().toString())
            WebViewHeaderManager.updateRefreshToken(tokenStorage.loadTokens()!!.refreshToken!!)
            WebViewHeaderManager.updateAccessToken(tokenStorage.loadTokens()!!.accessToken)
            logger.debug(WebViewHeaderManager.getHeaders().toString())
            logger.warn("Ingresando a MenuDrawer")
            MenuDrawer(onLogout = {
                /**
                 * It’s used to remove destinations from the back stack when navigating to another destination.
                 * If the user is logged, the user will be redirected to the MenuDrawer screen and couldn’t go
                 * back to the login screen.
                 */
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            })
        }
    }
}

/**
 * APP AMAZON
 */

@Composable
fun AppNavigation(onLogout: () -> Unit) {
    val logger = Logger("AppNavigation")

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val noBarsRoutes = listOf(Screen.Login.route, Screen.Profile.route)
    val showBars = !noBarsRoutes.contains(currentRoute)

    val updateVM = remember {
        UpdateVM(
            updateStorage = UpdateStorageImpl(
                settings = Settings()
            ),
            apiService = APIClient(
                updateStorage = UpdateStorageImpl(
                    settings = Settings()
                )
            )
        )
    }

    LaunchedEffect(Unit) {
        logger.warn("Checking for updates...")
        updateVM.checkForUpdates()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            /**
             * Para que el bottomBar no salga en la pantalla de Profile (Account) y en LoginScreen,
             * pero sí en las demás pantallas
             */
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
                    ProfileScreen(navController, onLogout = onLogout)
                }
                composable(route = Screen.Login.route) {
                    LoginScreen(navController)
                }
            }
        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )
    }
    if (updateVM.showUpdateDialog.collectAsState().value) {
        UpdateAlertDialog(viewModel = updateVM)
    }
}


/*@Composable
@Preview
fun AppPrueba() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}

class Greeting {
    private val platform = getPlatformType()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}*/