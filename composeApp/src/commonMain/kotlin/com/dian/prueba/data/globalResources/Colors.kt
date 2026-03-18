package com.dian.prueba.data.globalResources

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

/* *
 * Global colors
 */
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