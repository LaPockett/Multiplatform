package com.dian.prueba.ui.components.unused

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieClipSpec
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import multiplatform.composeapp.generated.resources.Res
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalHazeMaterialsApi::class)
@Preview
@Composable
fun ClippyLogo() {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/logolottie.json").decodeToString()
        )
    }
    //Controls the speed forward, backward and stop

    //Set the speed with respect to the control
    //Control animation state
    val progress by animateLottieCompositionAsState(
        composition = composition,
        restartOnPlay = true,
        speed = 1f,
        iterations = Compottie.IterateForever,
        //Specifies the bound the animation playback should be clipped to
        clipSpec = LottieClipSpec.Progress(0f, 1f)
    )
    val hazeState = rememberHazeState()

    Box(
        modifier = Modifier.background(Color.DarkGray)
    ){
        Image(
            modifier = Modifier.fillMaxWidth().hazeSource(hazeState),
            painter =rememberLottiePainter(
                composition = composition, progress = {progress}, enableMergePaths = true,
            ),
            contentDescription = "Lottie animation"
        )
        Card(
            modifier = Modifier.hazeEffect(state = hazeState, style = HazeMaterials.ultraThin()).fillMaxWidth()
        ){

        }
    }
}

/**
 * Medium - Lottie Files in CMP
 * Web: https://medium.com/@yogwaran5/lottie-files-in-compose-multiplatform-2bceb438cec9
 */
@Composable
fun Anims(controls: Controls) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/logolottie.json").decodeToString()
        )
    }
    //Controls the speed forward, backward and stop
    var speed by remember { mutableFloatStateOf(0f) }
    //Set the speed with respect to the control
    LaunchedEffect(controls) {
        speed = when (controls) {
            Controls.FORWARD -> 1f
            Controls.REVERSE -> -1f
            Controls.STOP -> 0f
        }
    }
    //Control animation state
    val progress by animateLottieCompositionAsState(
        composition = composition,
        restartOnPlay = true,
        speed = speed,
        iterations = Compottie.IterateForever,
        //Specifies the bound the animation playback should be clipped to
        clipSpec = LottieClipSpec.Progress(0f, 1f)
    )
    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(WindowInsets.safeDrawing.asPaddingValues())) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter =rememberLottiePainter(
                composition = composition, progress = {progress}, enableMergePaths = true,
            ),
            contentDescription = "Lottie animation"
        )
    }
}

enum class Controls {
    FORWARD, REVERSE, STOP
}