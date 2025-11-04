package com.dian.prueba.liquidglass.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset

@Composable
actual fun rememberUISensor(): UISensor {
    TODO("Not yet implemented")
}

actual class UISensor {
    actual val gravityAngle: Float
        get() = TODO("Not yet implemented")
    actual val gravity: Offset
        get() = TODO("Not yet implemented")
}