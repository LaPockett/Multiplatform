package com.dian.prueba.liquidglass.destinations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dian.prueba.liquidglass.components.LiquidButton
import com.dian.prueba.liquidglass.BackdropDemoScaffold
import org.jetbrains.compose.resources.painterResource
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.logo

@Composable
fun ButtonsContent() {
    BackdropDemoScaffold { backdrop ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16f.dp)
        ) {
            LiquidButton(
                {},
                backdrop
            ) {
                BasicText(
                    "Transparent Liquid Button",
                    style = TextStyle(Color.Black, 15f.sp)
                )
            }

            /*LiquidButton(
                {},
                backdrop
            ) {
                Icon(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }*/
            GlassClippyLogo(
                {},
                backdrop,
                surfaceColor = Color.White.copy(0.3f),
                ){
                Icon(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
            LiquidButton(
                {},
                backdrop,
                surfaceColor = Color.White.copy(0.3f)
            ) {
                BasicText(
                    "Surface Liquid Button",
                    style = TextStyle(Color.Black, 15f.sp)
                )
            }
            LiquidButton(
                {},
                backdrop,
                tint = Color(0xFF0088FF)
            ) {
                BasicText(
                    "Tinted Liquid Button",
                    style = TextStyle(Color.White, 15f.sp)
                )
            }
            LiquidButton(
                {},
                backdrop,
                tint = Color(0xFFFF8D28)
            ) {
                BasicText(
                    "Tinted Liquid Button",
                    style = TextStyle(Color.White, 15f.sp)
                )
            }
        }
    }
}
