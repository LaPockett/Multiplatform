package com.dian.prueba.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String,

)
val navigationItems = listOf(
    BottomNavigationItem("Home", Icons.Default.Home, "home"),
    BottomNavigationItem("Profile", Icons.Default.Person, "profile"),
    BottomNavigationItem("Cart", Icons.Default.ShoppingCart, "cart"),
    BottomNavigationItem("Settings", Icons.Default.Menu, "settings")
)
