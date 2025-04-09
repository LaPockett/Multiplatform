package com.dian.prueba.Navigation

sealed class Screen (val route: String){
    object Home: Screen("home")
    object Cart: Screen("cart")
    object Profile: Screen("profile")
    object Settings: Screen("settings")
}