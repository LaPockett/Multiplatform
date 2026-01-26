package com.dian.prueba.strings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.Lyricist
import cafe.adriel.lyricist.ProvideStrings
import cafe.adriel.lyricist.rememberStrings
import com.dian.prueba.modelNuFeed.NuFeedUIModel

@Composable
internal fun SampleApplication() {
    val strings = mapOf(
        Locales.EN to EnStrings,
        Locales.PT to PtStrings,
    )
    val lyricist = rememberStrings(
        defaultLanguageTag = "pt", currentLanguageTag = "en",
        translations = strings
    )
    val LocalStrings = staticCompositionLocalOf { EnStrings }
    ProvideStrings(lyricist, LocalStrings) {
        Column(
            modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(horizontal = 12.dp)
        ) {
            //SampleStrings(lyricist)

            Spacer(Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SwitchLocaleButton(
                    lyricist,
                    Locales.EN,
                    Modifier.weight(1f)
                )
                Spacer(Modifier.weight(.1f))
                SwitchLocaleButton(
                    lyricist,
                    Locales.PT,
                    Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
internal fun SampleStrings(lyricist: Lyricist<Strings>, item: NuFeedUIModel.MessageIn) {
    Column {
        Text(
            text = "Sample",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        // Simple simple
        //Text(text = lyricist.strings.simple)

        // Annotated string
        //Text(text = lyricist.strings.annotated)

        // Parameter string
        Text(text = lyricist.strings.parameter(item.text))

        // Plural string
        /*Text(text = lyricist.strings.plural(0))
        Text(text = lyricist.strings.plural(1))
        Text(text = lyricist.strings.plural(5))
        Text(text = lyricist.strings.plural(20))*/

        // List string
        //Text(text = lyricist.strings.list.joinToString())
    }
}

@Composable
internal fun SwitchLocaleButton(
    lyricist: Lyricist<Strings>,
    languageTag: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            lyricist.languageTag = languageTag
        },
        modifier = modifier
    ) {
        Text(text = languageTag)
    }
}