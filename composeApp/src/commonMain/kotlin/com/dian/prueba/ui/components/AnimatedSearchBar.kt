package com.dian.prueba.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.arjunjadeja.texty.DisplayStyle
import com.arjunjadeja.texty.RevealingCover
import com.arjunjadeja.texty.RevealingPattern
import com.arjunjadeja.texty.RevealingType
import com.arjunjadeja.texty.Texty
import com.dian.prueba.model.LocalColors
import kotlinx.coroutines.delay
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.animated_textfield1
import multiplatform.composeapp.generated.resources.animated_textfield2
import multiplatform.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AnimatedSearchBar() {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(WindowInsets.safeDrawing.asPaddingValues()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                query = "",
                onQueryChange = {}
            )
        }
    }
}

/**
 * Animated Placeholder
 * https://proandroiddev.com/animated-placeholder-with-jetpack-compose-60c85547b47a
 */
@Composable
fun AnimatedPlaceholder(
    hints: List<String>,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    delayBeforeRevealing: Long = 500L,
) {
    val iterator = hints.listIterator()

    val target by produceState(initialValue = hints.first()) {
        iterator.doWhenHasNextOrPrevious {
            value = it
        }
    }

    AnimatedContent(
        targetState = target,
        transitionSpec = { ScrollAnimation() }
    ) { str ->
        Texty(
            text = str,
            textStyle = textStyle,
            displayStyle = DisplayStyle.Revealing(
                delayBeforeRevealing = delayBeforeRevealing,
                pattern = RevealingPattern.START_TO_END,
                type = RevealingType.ByEachCharacter(delayInMillis = 30L),
                cover = RevealingCover.Custom(" ")
            ),
            maxLines = 1
        )
    }
}

suspend fun <T> ListIterator<T>.doWhenHasNextOrPrevious(
    delayMills: Long = 2400,
    doWork: suspend (T) -> Unit
) {
    while (hasNext() || hasPrevious()) {
        while (hasNext()) {
            delay(delayMills)
            doWork(next())
        }
        while (hasPrevious()) {
            delay(delayMills)
            doWork(previous())
        }
    }
}

object ScrollAnimation {
    @OptIn(ExperimentalAnimationApi::class)
    operator fun invoke(): ContentTransform {
        return slideInVertically(
            initialOffsetY = { 50 },
            animationSpec = tween()
        ) + fadeIn() togetherWith slideOutVertically(
            targetOffsetY = { -50 },
            animationSpec = tween()
        ) + fadeOut()
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = query,
        onValueChange = onQueryChange,
        enabled = false,
        readOnly = true,
        label = {
            AnimatedPlaceholder(
                hints = listOf(
                    stringResource(Res.string.animated_textfield1),
                    stringResource(Res.string.animated_textfield2),
                    "Pick your bag!",
                ),
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }
    )
}

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String,
    delayBeforeRevealing: Long = 500L,
) {
    val colorModifier = LocalColors.current
    OutlinedTextField(
        modifier = modifier,
        value = query,
        onValueChange = onQueryChange,
        enabled = false,
        readOnly = true,
        label = {
            AnimatedPlaceholder(
                hints = listOf(placeholder, ""),
                delayBeforeRevealing = delayBeforeRevealing
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Search",
                tint = colorModifier.logoColorMessage,
                modifier = Modifier.size(24.dp)
            )
        }
    )
}
