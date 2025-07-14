package com.dian.prueba.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.dian.prueba.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                //Log.d(TAG, token)
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                })
            AppLogin()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    AppLogin()
}
