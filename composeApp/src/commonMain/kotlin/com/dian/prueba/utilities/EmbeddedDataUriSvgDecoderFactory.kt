package com.dian.prueba.utilities

import coil3.ImageLoader
import coil3.decode.Decoder
import coil3.decode.ImageSource
import coil3.fetch.SourceFetchResult
import coil3.request.Options
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import okio.Buffer

/**
 * Work around SVGs that wrap embedded raster payloads via data URIs.
 */
class EmbeddedDataUriSvgDecoderFactory : Decoder.Factory {

    override fun create(
        result: SourceFetchResult,
        options: Options,
        imageLoader: ImageLoader,
    ): Decoder? {
        if (result.mimeType != "image/svg+xml") return null

        return EmbeddedDataUriSvgDecoder(
            original = result,
            options = options,
            imageLoader = imageLoader,
        )
    }
}

private class EmbeddedDataUriSvgDecoder(
    private val original: SourceFetchResult,
    private val options: Options,
    private val imageLoader: ImageLoader,
) : Decoder {

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun decode() = run {
        val svgBytes = original.source.source().readByteArray()
        val svgText = svgBytes.decodeToString()

        val match = DATA_URI_IN_SVG.find(svgText)
        if (match == null) {
            return null
        }

        val embeddedMimeType = match.groupValues[1].lowercase()
        val embeddedBase64 = match.groupValues[2].filterNot(Char::isWhitespace)
        val embeddedBytes = Base64.decode(embeddedBase64)

        val rewrittenResult = SourceFetchResult(
            source = ImageSource(
                source = Buffer().apply { write(embeddedBytes) },
                fileSystem = options.fileSystem,
            ),
            mimeType = embeddedMimeType,
            dataSource = original.dataSource,
        )

        try {
            val next = checkNotNull(
                imageLoader.components.newDecoder(
                    result = rewrittenResult,
                    options = options,
                    imageLoader = imageLoader,
                )
            ) { "No downstream decoder found for mimeType=${rewrittenResult.mimeType}" }

            next.first.decode()
        } finally {
            rewrittenResult.source.close()
        }
    }

    private companion object {
        val DATA_URI_IN_SVG = Regex(
            pattern = """(?:xlink:href|href)\s*=\s*['"]data:([^;,'"]+)(?:;charset=[^;,'"]+)?;base64,([^'"]+)['"]""",
            option = RegexOption.IGNORE_CASE,
        )
    }
}