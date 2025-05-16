package com.dian.prueba.ui.components

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.dian.prueba.AppNavigation
import kotlinx.coroutines.launch

/**
 * EN USO
 */
@Composable
fun MenuDrawer3() {
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val scope = rememberCoroutineScope()
    scope.launch {
        if (drawerState.isClosed) {
            drawerState.isOpen
        } else {
            drawerState.isClosed
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        scrimColor = Color(0xFF080e45),
        drawerContent = {
            TopAppBarScreen4()
        },
        content = {
            AppNavigation()
        }
    )

}
