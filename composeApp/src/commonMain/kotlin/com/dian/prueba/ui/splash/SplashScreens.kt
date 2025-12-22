package com.dian.prueba.ui.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dian.prueba.model.LocalColors
import com.dian.prueba.model.LocalPadding
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.dian.prueba.ui.screens.ImageLogo
import kotlinx.coroutines.delay
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.button_splash_screen
import multiplatform.composeapp.generated.resources.first_splash_screen
import multiplatform.composeapp.generated.resources.logotitle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CentralSplashScreen(
    onFinish: () -> Unit
) {
    val paddingModifier = LocalPadding.current
    val colorModifier = LocalColors.current
    var showTitle by remember { mutableStateOf(false) }
    var showButton by rememberSaveable { mutableStateOf(false) }
    var showMsg by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(1000)
        showMsg = true
        delay(3500)
        showMsg = false
        delay(1100)
        showTitle = true
        delay(1700)
        showButton = true
        delay(2000)
    }
    MultiplatformTheme {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(colorModifier.backgroundSplash),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 75.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SmoothAppear(visible = showMsg) {
                    Text(
                        text = stringResource(Res.string.first_splash_screen),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        lineHeight = 34.sp
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SmoothAppear(visible = showTitle) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Welcome to",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraLight
                        )
                        Spacer(Modifier.padding(horizontal = paddingModifier.extraTiny))
                        ImageLogo(
                            tint = Color.White,
                            painter = painterResource(Res.drawable.logotitle),
                            modifier = Modifier.height(48.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = paddingModifier.extraLarge))
                SmoothAppear(visible = showButton) {
                    Button(
                        onClick = { onFinish() },
                        modifier = Modifier.height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorModifier.containerColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = stringResource(Res.string.button_splash_screen),
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

@Composable
fun SmoothAppear(
    visible: Boolean = true,
    duration: Int = 900,
    content: @Composable () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(duration)
    )
    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else 20.dp,
        animationSpec = tween(duration)
    )

    Box(
        modifier = Modifier
            .graphicsLayer {
                this.alpha = alpha
                translationY = offsetY.toPx()
            }
    ) {
        content()
    }
}