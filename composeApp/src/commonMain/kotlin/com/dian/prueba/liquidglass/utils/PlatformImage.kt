package com.dian.prueba.liquidglass.utils

import androidx.compose.ui.graphics.ImageBitmap

expect class PlatformImage {
    val data: ByteArray
    val width: Int
    val height: Int

    fun toImageBitmap(): ImageBitmap
}