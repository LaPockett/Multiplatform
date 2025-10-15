package com.dian.prueba.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.*
import androidx.compose.animation.with
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.arjunjadeja.texty.DisplayStyle
import com.arjunjadeja.texty.RevealingCover
import com.arjunjadeja.texty.RevealingPattern
import com.arjunjadeja.texty.RevealingType
import com.arjunjadeja.texty.Texty
import kotlinx.coroutines.delay
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.ysl
import multiplatform.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
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
                delayBeforeRevealing = 500L,
                pattern = RevealingPattern.START_TO_END,
                type = RevealingType.ByEachCharacter(delayInMillis = 30L),
                cover = RevealingCover.Custom(" ")
            )
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
        ) + fadeIn() with slideOutVertically(
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
    TextField(
        modifier = modifier,
        value = query,
        onValueChange = onQueryChange,
        label = {
            AnimatedPlaceholder(
                hints = listOf(
                    "Hey Maria! Here are the perfect pieces for your week",
                    "What will you choose?",
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

@Preview
@Composable
fun FeedLogo() {
    MaterialTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().statusBarsPadding().padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "Logo de Amazon",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                OutlinedCard(
                    modifier = Modifier
                        .height(200.dp).fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f).padding(end = 8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Title of the card",
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                            Text(
                                text = "Body. Lorem ipsum dolor sit amet consectetur adipiscing elit convallis montes porta interdum tortor.",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                        Image(
                            painter = painterResource(Res.drawable.ysl),
                            contentDescription = "Logo de Amazon",
                            modifier = Modifier.size(175.dp),
                            contentScale = ContentScale.Crop
                        )

                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ){
                    Text(
                        text = "Your daily inspiration",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 10.dp),
                    )
                }
                // Here Search Bar with animated placeholder
                    SearchBar(
                        query = "",
                        onQueryChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                Spacer(modifier = Modifier.padding(10.dp))

                // Here Grid Layout with 2 columns
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    items(22) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            shape = RoundedCornerShape(6.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        ){
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Item $it",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Image(
                                    painter = painterResource(Res.drawable.ysl),
                                    contentDescription = "Logo de Amazon",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

