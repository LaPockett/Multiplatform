package com.dian.prueba.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dian.prueba.AppNavigation
import com.dian.prueba.PlatformType
import com.dian.prueba.getPlatformType
import com.dian.prueba.ui.screens.SettingsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDrawer(onLogout: () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Box (
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(modifier = Modifier.fillMaxHeight()) {
                    /**
                     * This is done this way because iOS saves the scroll, while Android doesn´t.
                     * If the same code were left, it would conflict wit the iOS platform and the
                     * drawer would appear apparently empty.
                     */
                    Column (
                        modifier = if(getPlatformType() === PlatformType.ANDROID){
                            Modifier.verticalScroll(rememberScrollState()).fillMaxHeight()
                        }else{
                            Modifier.fillMaxHeight()
                        }
                    )
                    {
                        SettingsScreen()
                    }
                }
            },
            gesturesEnabled = drawerState.isOpen,
            scrimColor = MaterialTheme.colorScheme.scrim,
            modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            Scaffold(
                topBar = {
                    TopAppBarMenuDrawer(
                        drawerState = drawerState,
                        scope = scope,
                        title = "Menu Drawer"
                    )
                },
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize().padding(it)
                ){
                    Box{
                        AppNavigation(onLogout = onLogout)
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMenuDrawer(
    drawerState: DrawerState,
    scope: CoroutineScope,
    title: String
) {
    TopAppBar(
        title = { Text(text=title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimaryContainer) },
        windowInsets = WindowInsets(
            top = 0,
            bottom = 0
        ),
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = Color.White,
            navigationIconContentColor = Color.Black,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black
        ),
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Menú")
            }
        }
    )
}
