package com.dian.prueba
import androidx.compose.ui.window.ComposeUIViewController
import com.dian.prueba.ui.screens.LogoNavigation
import com.dian.prueba.liquidglass.destinations.BottomTabsContent
fun MainViewController() = ComposeUIViewController { BottomTabsContent() }