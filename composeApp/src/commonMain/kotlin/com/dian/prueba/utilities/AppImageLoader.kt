package com.dian.prueba.utilities

import coil3.ImageLoader
import coil3.PlatformContext

object AppImageLoader {
    fun create(context: PlatformContext): ImageLoader =
    ImageLoader.Builder(context)
            .components {
                add(EmbeddedDataUriSvgDecoderFactory())
            }
            .build()
    }
