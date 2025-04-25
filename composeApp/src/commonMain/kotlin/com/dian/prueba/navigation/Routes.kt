package com.dian.prueba.navigation

sealed class Screen (val route: String){
    object Home: Screen("home")
    object Cart: Screen("cart")
    object Profile: Screen("profile")
    object Explore: Screen("explore")
}