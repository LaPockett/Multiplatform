package com.dian.prueba.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.*
import androidx.compose.animation.with
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.arjunjadeja.texty.DisplayStyle
import com.arjunjadeja.texty.RevealingCover
import com.arjunjadeja.texty.RevealingPattern
import com.arjunjadeja.texty.RevealingType
import com.arjunjadeja.texty.Texty
import com.dian.prueba.navigation.navigationItemsLogo
import com.dian.prueba.ui.Theme.MultiplatformTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.lechiquito
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


@Composable
fun HeaderLogo() {
    MultiplatformTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedCard(
                modifier = Modifier
                    .height(180.dp).fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(12.dp).background(Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f).padding(end = 8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Title of the card",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        Text(
                            text = "Body. Lorem ipsum dolor sit amet consectetur adipiscing elit convallis montes porta.",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                    Image(
                        painter = painterResource(Res.drawable.ysl),
                        contentDescription = "Logo de Amazon",
                        modifier = Modifier.size(160.dp),
                        contentScale = ContentScale.Crop
                    )

                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
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

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Preview
@Composable
fun FeedLogo() {
    MultiplatformTheme {
        val hazeState = rememberHazeState()
        val navController = rememberNavController()

        val lightAlpha = 0.3f
        val darkAlpha = 0.1f
        val hazeStyle = HazeStyle(
            backgroundColor = Color.White,
            tints = listOf(
                HazeTint(
                    Color.White.copy(alpha = if (Color.White.luminance() >= 0.5) lightAlpha else darkAlpha),
                )
            ),
            blurRadius = 50.dp,
            noiseFactor = -1f,
            fallbackTint = HazeTint.Unspecified,
        )
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigationBarLogo(navController, hazeState, hazeStyle)
            },
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.fillMaxWidth()
                        .hazeEffect(
                            state = hazeState,
                            style = hazeStyle,
                        ),
                    windowInsets = WindowInsets.statusBars,
                    colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                    title = {
                        ImageLogo(tint = Color.Black)
                    }
                )
            }
        ) { innerPadding ->
            val graph = navController.createGraph(startDestination = "closet") {
                composable(route = "closet") {
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)//.statusBarsPadding(),
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(3.dp),
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                            modifier = Modifier.hazeSource(hazeState).fillMaxSize(),
                            //contentPadding = PaddingValues(top = 70.dp, bottom = 24.dp)
                        ) {
                            item(
                                span = { GridItemSpan(2) }
                            ) {
                                // Header of the feed
                                HeaderLogo()
                            }
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
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Item $it",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Image(
                                            painter = painterResource(Res.drawable.lechiquito),
                                            contentDescription = "Logo de Amazon",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }
                        }

                        val interactionSource = remember { MutableInteractionSource() }
                        Card(
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier.size(50.dp) // Sí que está pero detrás del bottom navigation
                                .align(Alignment.BottomEnd)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    onClick = { }
                                )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize().hazeEffect(
                                    state = hazeState,
                                    style = HazeMaterials.ultraThin()
                                ),
                                contentAlignment = Alignment.Center
                            ) {

                                ImageLogo(tint = Color.White, size = 30.dp)

                            }

                        }
                    }
                }
                composable(route = "profile") {
                    Text("Profile Screen")
                }
            }
            NavHost(
                navController = navController,
                graph = graph,
                modifier = Modifier.padding(innerPadding)
            )

        }
    }

}


@Composable
fun ImageLogo(
    tint: Color,
    size: Dp = 55.dp
) {
    Icon(
        painter = painterResource(Res.drawable.logo),
        contentDescription = "Logo de Logo Circular Luxury Closet",
        modifier = Modifier.size(size),
        tint = tint
    )
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun BottomNavigationBarLogo(navController: NavController, hazeState : HazeState, hazeStyle: HazeStyle) {

    val selectedNavigationIndex = rememberSaveable { mutableStateOf(0) }
    BottomAppBar(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary,
        windowInsets = WindowInsets.navigationBars,
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp, 30.dp))
            .fillMaxWidth()
            .hazeEffect(
            state = hazeState,
            style = hazeStyle
        )
    ) {
        navigationItemsLogo.forEachIndexed { index, tab ->
            NavigationBarItem(
                selected = selectedNavigationIndex.value == index,
                onClick = {
                    selectedNavigationIndex.value = index
                    navController.navigate(tab.route)
                },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title
                    )
                },
                label = {
                    Text(text = tab.title, style = MaterialTheme.typography.bodyMedium)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary,
                    indicatorColor = Color.White
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ModalBottomSheetMaterial3Sample() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    MultiplatformTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bottom Sheet Sample Material 3")
            Spacer(Modifier.height(20.dp))
            Button(onClick = { scope.launch { sheetState.show() } }) {
                Text("Click to show sheet")
            }
        }
        if (sheetState.isVisible) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
            ) {
                LazyColumn {
                    items(50) {
                        ListItem(
                            headlineContent = { Text("Item $it") },
                            leadingContent = {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = "Localized description"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

