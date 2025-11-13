package com.dian.prueba.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dian.prueba.model.LocalPadding
import com.dian.prueba.navigation.ScreenBottom
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.dian.prueba.ui.screens.ImageLogo
import com.lapockett.lib.buttons.ButtonCustom
import kotlinx.coroutines.delay
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.logotitle
import multiplatform.composeapp.generated.resources.second_splash_screen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun FirstSplash(
    onFinish: () -> Unit
) {
    LaunchedEffect(
        key1 = Unit,
        block = {
            delay(3000)
            onFinish()
        }
    )
    MultiplatformTheme {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black)
                .padding(horizontal = 100.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Hemos revisado cuidadosamente tu solicitud de acceso y tenemos un veredicto",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Light
            )
        }
    }

}


@Preview
@Composable
fun Secondplash(
    onFinish: () -> Unit
) {
    MultiplatformTheme {
        val paddingModifier = LocalPadding.current

        var showButton by remember { mutableStateOf(false) }
        var showTitle by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(2500)
            showButton = true
        }
        LaunchedEffect(Unit) {
            delay(900)
            showTitle = true
        }

        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = showTitle,
                    enter = fadeIn(animationSpec = tween(900)) + slideInVertically(initialOffsetY = { it / 2 }),
                    exit = fadeOut()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.second_splash_screen),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraLight
                        )
                        Spacer(
                            modifier = Modifier.padding(horizontal = paddingModifier.extraTiny)
                        )
                        ImageLogo(
                            tint = Color.White,
                            painter = painterResource(Res.drawable.logotitle),
                            modifier = Modifier.height(48.dp)
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.padding(vertical = paddingModifier.extraLarge)
                )
                AnimatedVisibility(
                    visible = showButton,
                    enter = fadeIn(animationSpec = tween(900)) + slideInVertically(initialOffsetY = { it / 2 }),
                    exit = fadeOut()
                ) {
                Button(
                    onClick = {
                        onFinish()
                    },
                    modifier = Modifier
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffb7af98),
                        contentColor = Color.White
                    )
                    ){
                    Text(
                        text = "Acceder a mi closet",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraLight
                    )
                }
                }
            }
        }
    }
}