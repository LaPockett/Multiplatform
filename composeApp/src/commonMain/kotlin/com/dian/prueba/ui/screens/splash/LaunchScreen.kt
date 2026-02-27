package com.dian.prueba.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dian.prueba.data.globalResources.LocalColors
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.coroutines.delay
import multiplatform.composeapp.generated.resources.Res

/**
 * In progress
 */
@Composable
fun LogoLottie(
) {
    val colorModifier = LocalColors.current
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/logo_launch.json").decodeToString()
        )
    }
    val progress by animateLottieCompositionAsState(composition)
    var showLottie by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        showLottie = true
        delay(1200)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorModifier.backgroundApp),
        contentAlignment = Alignment.Center
    ) {
        SmoothAppear(visible = showLottie) {
            Image(
                painter = rememberLottiePainter(
                    composition = composition,
                    progress = { progress },
                ),
                contentDescription = "Lottie animation"
            )
        }

    }
}