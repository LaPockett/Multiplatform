package com.dian.prueba.ui.components

import androidx.compose.material.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.dian.prueba.AppNavigation
import com.dian.prueba.ui.screens.CartScreen

@Composable
fun MenuDrawer() {
    val drawerState = rememberDrawerState(DrawerValue.Open)
    var gesturesEnabled by remember { mutableStateOf(false) }
    if (drawerState.isOpen){
        gesturesEnabled = true
    } else {
        gesturesEnabled = false
    }
    ModalNavigationDrawer(
        drawerContent = {
            TopAppBarScreen4()
            //CartScreen()
        },
        drawerState = drawerState,
        scrimColor = Color(0xFF080e45),
        gesturesEnabled = gesturesEnabled,
        //modifier = Modifier. ,
        content = {
            AppNavigation()
        }
    )
}

@Composable
fun MenuDrawer2() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFF080e45),
                drawerShape = androidx.compose.material.MaterialTheme.shapes.small
            ) {
                CartScreen()
            }
        },
        drawerState = drawerState,
        scrimColor = Color(0xFF080e45),
        gesturesEnabled = true,
        //modifier = Modifier. ,
        content = {
            AppNavigation()
        }
    )
}
