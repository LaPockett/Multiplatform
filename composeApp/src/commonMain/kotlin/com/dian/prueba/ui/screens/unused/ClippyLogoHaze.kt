package com.dian.prueba.ui.screens.unused

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.dian.prueba.ui.screens.navigation.ImageLogo
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect

/**
 * Clippy Logo with Haze
 */
@Composable
fun ClippyLogo(
    hazeState: HazeState,
    hazeStyle: HazeStyle
) {
    Box(
        modifier = Modifier.graphicsLayer {
            translationX = 4.4.dp.toPx()
            translationY = 12.dp.toPx()
        }
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(45.dp)
                .background(Color.Transparent)
                .hazeEffect(state = hazeState, style = hazeStyle)
                .clickable { /* POR IMPLEMENTAR */ },
            contentAlignment = Alignment.Center
        ) {
            ImageLogo(tint = Color.White, modifier = Modifier.size(24.dp))
        }
    }
}