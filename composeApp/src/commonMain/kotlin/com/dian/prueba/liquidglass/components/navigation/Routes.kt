package com.dian.prueba.liquidglass.components.navigation

sealed class Screen (val route: String){
    object Home: Screen("home")
    object Cart: Screen("cart")
    object Profile: Screen("profile")
    object Explore: Screen("explore")
    object Login : Screen("login")
}