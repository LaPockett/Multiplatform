package com.dian.prueba

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.dian.prueba.API.APIClient
import com.dian.prueba.Navigation.Screen
import com.dian.prueba.Navigation.navigationItems
import com.dian.prueba.Screens.*
import com.dian.prueba.ui.BrandScreen
import com.dian.prueba.ui.WelcomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

//expo react native
// cpu bench
// mirar hot reload en Android Studio
@Composable
@Preview
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

/**
 * APP AMAZON
 */
@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val showBars = currentRoute != Screen.Profile.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val selectedNavigationIndex = rememberSaveable { mutableStateOf(0) }
    NavigationBar(
        containerColor = Color.White
    ) {
        navigationItems.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = selectedNavigationIndex.value == index,
                onClick = {
                    selectedNavigationIndex.value = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title,
                        color = if (index == selectedNavigationIndex.value)
                            Color.Black
                        else Color.Gray
                    )
                }
            )
        }
    }
}


