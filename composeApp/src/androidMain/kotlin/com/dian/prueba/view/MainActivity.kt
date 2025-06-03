package com.dian.prueba.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.dian.prueba.AppLogin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            //enableEdgeToEdge()
            val darkColor = Color.Transparent
            val lightColor = Color.Transparent
            val isDarkTheme = isSystemInDarkTheme()
            enableEdgeToEdge(
                statusBarStyle = if (!isDarkTheme){
                    SystemBarStyle.dark(darkColor.hashCode())
                } else SystemBarStyle.light(lightColor.hashCode(), lightColor.hashCode()),
                navigationBarStyle = if (!isDarkTheme){
                    SystemBarStyle.dark(darkColor.hashCode())
                } else SystemBarStyle.light(lightColor.hashCode(), lightColor.hashCode())
            )
            /**
             * Esto es para que los datos del status bar se vean dependiendo del tema
             */
            val view= LocalView.current
            SideEffect {
                val window = (view.context as ComponentActivity).window
                window.statusBarColor = Color.Transparent.toArgb()
                WindowCompat.getInsetsController(window,view).isAppearanceLightStatusBars = !isDarkTheme
                window.navigationBarColor = Color.Transparent.toArgb()
                WindowCompat.getInsetsController(window,view).isAppearanceLightNavigationBars = !isDarkTheme
            }
            //App()
            //AppNavigation()
            AppLogin()
            //MenuDrawer()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    //AppNavigation()
    AppLogin()
    //MenuDrawer()
    //App()
}
