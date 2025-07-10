package com.dian.prueba.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController) {
    val selectedNavigationIndex = rememberSaveable { mutableStateOf(0) }
    NavigationBar(
        containerColor = Color(0xffd6dbed),
        contentColor = Color(0xFF080e45),
        windowInsets = WindowInsets(
            top = 0,
            bottom = 0
        )
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
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
                    Text(text = item.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF2E3B97),
                    unselectedIconColor = Color(0xFF080E45),
                    selectedTextColor = Color(0xFF2E3B97),
                    unselectedTextColor = Color(0xFF080E45),
                    indicatorColor = Color.White
                )
            )
        }
    }
}