package com.dian.prueba.data.model

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Global padding
 */
data class Padding(
    val extraTiny : Dp = 3.dp,
    val tiny : Dp = 8.dp,
    val small: Dp = 12.dp,
    val normal: Dp = 16.dp,
    val big: Dp = 20.dp,
    val large: Dp = 24.dp,
    val extraBig : Dp = 32.dp,
    val extraLarge : Dp = 36.dp
)

val LocalPadding = compositionLocalOf { Padding() }

data class Dimensions(
    val viewTiny : Dp = 40.dp,
    val viewSmall :Dp = 48.dp,
    val viewNormal: Dp = 56.dp,
    val viewBig: Dp = 64.dp,
    val viewLarge: Dp = 72.dp,
    val iconTiny: Dp = 16.dp,
    val iconSmall: Dp = 20.dp,
    val iconNormal: Dp = 24.dp,
    val iconBig: Dp = 28.dp,
    val iconLarge: Dp = 32.dp
)

val LocalDimension = compositionLocalOf { Dimensions() }

data class Colors(
    val backgroundApp : Color = Color(0xfffdfbff),
    val backgroundSplash : Color = Color(0xff000000),
    val containerColor: Color = Color(0xffa69d83),
    val containerLightColorLogo : Color = Color(0xffb7af98),
    val logoColorMessage : Color = Color(0xff171717),
    val logoColor : Color = Color(0xffb8b89c),
    val logoColorLight : Color = Color(0xffebebe4),
    val blackLight : Color = Color(0xff7b7c77),
    val dividerLight : Color = Color(0xffefefef)
)
val LocalColors = compositionLocalOf { Colors() }