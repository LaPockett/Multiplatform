package com.dian.prueba.data.globalResources

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/* *
 * Global padding
 */
data class Padding(
    val extraTiny : Dp = 4.dp,
    val tiny : Dp = 8.dp,
    val small: Dp = 12.dp,
    val normal: Dp = 16.dp,
    val big: Dp = 20.dp,
    val large: Dp = 24.dp,
    val largeBig: Dp = 28.dp,
    val extraBig : Dp = 32.dp,
    val extraLarge : Dp = 36.dp,
    val extraExtraLarge : Dp = 40.dp
)

val LocalPadding = compositionLocalOf { Padding() }