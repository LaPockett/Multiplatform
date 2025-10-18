package com.dian.prueba.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.dian.prueba.ui.Theme.MultiplatformTheme
import dev.chrisbanes.haze.HazeState
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.matcha
import multiplatform.composeapp.generated.resources.ysl
import org.jetbrains.compose.resources.painterResource

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
    BottomNavigationItemLogo("Newspaper", Icons.Default.Email, "newspaper"),
    BottomNavigationItemLogo("Closet", Icons.Default.Notifications, "closet"),
    BottomNavigationItemLogo("Profile", Icons.Default.Person, "profile")
)

/**
 * Para ver su comportamiento junto con el bottom navigation
 */
@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.padding(WindowInsets.safeDrawing.asPaddingValues())
        ) {
            items(22){
                Image(
                    painter = painterResource(Res.drawable.matcha),
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

    }
}

@Composable
fun NewspaperScreen() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn (
            modifier = Modifier.padding(WindowInsets.safeDrawing.asPaddingValues())
        ){
            items(22){
                Image(
                    painter = painterResource(Res.drawable.ysl),
                    contentDescription = "Newspaper",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

    }
}