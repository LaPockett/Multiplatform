package com.dian.prueba.ui.screens.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.dian.prueba.data.globalResources.LocalColors
import com.dian.prueba.data.globalResources.LocalPadding
import com.kyant.backdrop.backdrops.layerBackdrop
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownImage
import com.mikepenz.markdown.compose.elements.MarkdownParagraph
import com.mikepenz.markdown.coil3.Coil3ImageTransformerImpl
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.rememberMarkdownState
import multiplatform.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsletterScreen(
    navController: NavHostController
) {
    val colorModifier = LocalColors.current
    val paddingModifier = LocalPadding.current

    val components = markdownComponents(
        // !error: Image is animating to actual size on Android...
        // ref: https://github.com/mikepenz/multiplatform-markdown-renderer/issues/430
        image = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MarkdownImage(it.content, it.node)
                }
            }
        },
        paragraph = {
            Column {
                MarkdownParagraph(it.content, it.node)
                Spacer(
                    Modifier.padding(vertical = paddingModifier.extraTiny)
                )
            }
        },
    )
    AppScaffold(
        navController = navController,
    ) { paddingValues, backdrop ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop)
                .background(colorModifier.backgroundApp)
                .padding(horizontal = paddingModifier.tiny),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
                    .padding(
                        top = paddingValues.calculateTopPadding() + paddingModifier.tiny,
                        bottom = paddingValues.calculateBottomPadding()
                    ),
            ) {
                Markdown(
                    markdownState = rememberMarkdownState {
                        Res.readBytes("files/example.md").decodeToString()
                    },
                    components = components,
                    typography = markdownTypography(
                        h1 = MaterialTheme.typography.titleLarge.copy(
                            color = colorModifier.logoColor
                        ),
                        h2 = MaterialTheme.typography.titleMedium.copy(
                            color = colorModifier.logoColor
                        ),
                        h3 = MaterialTheme.typography.titleSmall.copy(
                            color = colorModifier.logoColor
                        ),
                        text = MaterialTheme.typography.bodyMedium.copy(
                            color = colorModifier.blackLight
                        ),
                        quote = MaterialTheme.typography.bodySmall.copy(
                            color = colorModifier.logoColor,
                            fontStyle = FontStyle.Italic
                        ),
                        paragraph = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Black,
                            textAlign = TextAlign.Justify
                        ),
                        textLink = TextLinkStyles(
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.Blue
                            ).toSpanStyle()
                        ),
                        ordered = MaterialTheme.typography.titleSmall.copy(
                            color = Color.Blue
                        )
                    ),
                    imageTransformer = Coil3ImageTransformerImpl,
                )
            }
        }
    }
}