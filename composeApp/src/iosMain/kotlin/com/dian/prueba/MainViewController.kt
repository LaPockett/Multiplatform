package com.dian.prueba
import androidx.compose.ui.window.ComposeUIViewController
import com.dian.prueba.ui.screens.RootNavigation
import com.dian.prueba.ui.splash.CentralSplashScreen

fun MainViewController() = ComposeUIViewController { CentralSplashScreen() }