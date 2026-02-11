package com.dian.prueba.view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.animation.doOnEnd
import androidx.core.view.WindowCompat
import com.dian.prueba.R
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.dian.prueba.ui.splash.CentralSplashScreen
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import com.mmk.kmpnotifier.permission.permissionUtil

// Define root module for Showkase
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Ask for notification permission
        val permissionUtil by permissionUtil()
        permissionUtil.askNotificationPermission()
        //Initialize NotifierManager from KMPNotifier
        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.logo,
                showPushNotification = true
            )
        )
        //To see internal logs of NotifierManager
        NotifierManager.setLogger { message ->
            println("NotifierManager: $message")
        }
        /**
         * To customize the exit of the splash screen animation
         */
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val fadeOut = ObjectAnimator.ofFloat(
                splashScreenView,
                View.ALPHA,
                1f,
                0f)
            fadeOut.interpolator = AccelerateInterpolator()
            fadeOut.duration = 800L
            fadeOut.doOnEnd { splashScreenView.remove() }
            fadeOut.start()
        }
        setContent {
            // To open Showkase
            val darkColor = Color.Transparent
            val lightColor = Color.Transparent
            val isDarkTheme = isSystemInDarkTheme()
            // Enable edge-to-edge display (content extends behind system bars)
            enableEdgeToEdge(
                // Status bar style: dark/light based on theme with transparent background
                statusBarStyle = if (!isDarkTheme) {
                    SystemBarStyle.dark(darkColor.hashCode())
                } else SystemBarStyle.light(lightColor.hashCode(), lightColor.hashCode()),
                // Navigation bar style: same logic as status bar
                navigationBarStyle = if (!isDarkTheme) {
                    SystemBarStyle.dark(darkColor.hashCode())
                } else SystemBarStyle.light(lightColor.hashCode(), lightColor.hashCode())
            )

            val view = LocalView.current
            SideEffect {
                val window = (view.context as ComponentActivity).window
                //Makes status bar transparent
                window.statusBarColor = Color.Transparent.toArgb()
                //Adjusts icons (light/dark) based on theme
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                    !isDarkTheme
                //Same configuration for navigation bar
                window.navigationBarColor = Color.Transparent.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                    !isDarkTheme
            }
            //To get fcm token and send a local test notification
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                //Log.d(TAG, token) -> FCM token
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
            MultiplatformTheme {
                CentralSplashScreen()
            }
        }
    }
}

/*
// Basic Usage of Compottie
@Composable
fun LottieLogo() {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/logolottie.json").decodeToString()
        )
    }
    val progress by animateLottieCompositionAsState(composition)

    Image(
        painter = rememberLottiePainter(
            composition = composition,
            progress = { progress },
        ),
        contentDescription = "Lottie animation"
    )
}*/