package com.dian.prueba.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dian.prueba.data.globalResources.Colors
import com.dian.prueba.data.globalResources.Dimensions
import com.dian.prueba.data.globalResources.Padding
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun BubbleMessage(
    text: String,
    isFromMe: Boolean,
    colorModifier: Colors,
    dimensionModifier: Dimensions,
    paddingModifier: Padding
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isFromMe) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!isFromMe) {
            Icon(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Logo",
                tint = Color.Black,
                modifier = Modifier.size(dimensionModifier.iconBig)
            )
            Spacer(modifier = Modifier.padding(horizontal = paddingModifier.tiny))
        }

        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (isFromMe) 48f else 0f,
                        bottomEnd = if (isFromMe) 0f else 48f
                    )
                )
                .background(if (isFromMe) Color.LightGray else colorModifier.logoColor)
                .padding(16.dp),
            contentAlignment = if (isFromMe) Alignment.CenterEnd else Alignment.CenterStart
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
            )
        }

        if (isFromMe) {
            Spacer(modifier = Modifier.padding(horizontal = paddingModifier.tiny))
        }
    }
}
