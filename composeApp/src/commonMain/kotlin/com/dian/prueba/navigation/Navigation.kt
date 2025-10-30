package com.dian.prueba.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Bottom navigation de la app mock
 */
data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
)
val navigationItems = listOf(
    BottomNavigationItem("Home", Icons.Default.Home, "home"),
    BottomNavigationItem("Profile", Icons.Default.Person, "profile"),
    BottomNavigationItem("Cart", Icons.Default.ShoppingCart, "cart"),
    BottomNavigationItem("Explore", Icons.Default.Menu, "explore")
)

/**
 * Bottom navigation de feed de logo
 */
data class BottomNavigationItemLogo(
    val title: String,
    val icon: ImageVector,
    val route: String,
)
val navigationItemsLogo = listOf(
    BottomNavigationItemLogo("Newsletter", Icons.Default.Newspaper, "newspaper"),
    BottomNavigationItemLogo("Closet", Icons.Default.Notifications, "closet"),
    BottomNavigationItemLogo("Profile", Icons.Default.Person, "profile")
)