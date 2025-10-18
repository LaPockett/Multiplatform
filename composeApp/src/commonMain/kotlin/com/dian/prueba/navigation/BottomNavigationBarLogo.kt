package com.dian.prueba.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dian.prueba.ui.Theme.MultiplatformTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.launch
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.lechiquito
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun BottomNavigationBarLogo(
    navController: NavController,
    hazeState: HazeState
) {
    val lightAlpha = 0.3f
    val darkAlpha = 0.1f
    val hazeStyle = HazeStyle(
        backgroundColor = Color.Transparent,
        tints = listOf(
            HazeTint(
                Color.Transparent.copy(alpha = if (Color.Transparent.luminance() >= 0.5) lightAlpha else darkAlpha),
            )
        ),
        blurRadius = 5.dp,
        noiseFactor = -1f,
        fallbackTint = HazeTint.Unspecified,
    )

    val selectedNavigationIndex = rememberSaveable { mutableStateOf(1) }
    BottomAppBar(
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        windowInsets = WindowInsets.navigationBars,
        modifier = Modifier
            .clip(RoundedCornerShape(190.dp, 190.dp, bottomEnd = 190.dp, bottomStart = 190.dp))
            //.padding(start = 20.dp, end = 20.dp)
            .hazeEffect(
                state = hazeState,
                style = hazeStyle
            )
    ) {
        navigationItemsLogo.forEachIndexed { index, tab ->
            val isSelected = selectedNavigationIndex.value == index
            NavigationBarItem(
                selected = selectedNavigationIndex.value == index,
                onClick = {
                    selectedNavigationIndex.value = index
                    navController.navigate(tab.route)
                },
                icon = {
                    if (isSelected) {
                        AnimatedVisibility(
                            visible = isSelected,
                            enter = fadeIn(animationSpec = tween(300)) + scaleIn(animationSpec = tween(300, easing = FastOutSlowInEasing)),
                            exit = fadeOut(animationSpec = tween(300)) + scaleOut(animationSpec = tween(300, easing = FastOutSlowInEasing))
                        ) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                            )
                        }
                    } else {
                        AnimatedVisibility(
                            visible = !isSelected,
                            enter = fadeIn(animationSpec = tween(300)) + scaleIn(animationSpec = tween(300, easing = FastOutSlowInEasing)),
                            exit = fadeOut(animationSpec = tween(300)) + scaleOut(animationSpec = tween(300, easing = FastOutSlowInEasing))
                        ) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                            )
                        }
                    }
                },
                label = {
                    AnimatedVisibility(
                        visible = isSelected,
                        enter = fadeIn(animationSpec = tween(250)) + slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(250, easing = FastOutSlowInEasing)
                        ),
                        exit = fadeOut(animationSpec = tween(250)) + slideOutVertically(
                            targetOffsetY = { it / 2 },
                            animationSpec = tween(250, easing = FastOutSlowInEasing)
                        )
                    ) {
                        Text(text = tab.title, style = MaterialTheme.typography.titleSmall)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xffffffff),
                    unselectedIconColor = Color(0x84ffffff),
                    selectedTextColor = Color(0xffffffff),
                    unselectedTextColor = Color(0x84ffffff),
                    indicatorColor =  Color(0x00ffffff)
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Preview
@Composable
fun TestHaze() {
    MultiplatformTheme {
        val hazeState = rememberHazeState()

        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    // Pass it the HazeState we stored above
                    .hazeSource(state = hazeState)
            ) {
                items(22) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
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
                                text = "Itemfdfdfdfdfdfdfdfdfd $it",
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

            LargeTopAppBar(
                // Need to make app bar transparent to see the content behind
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                modifier = Modifier
                    // We use hazeEffect on anything where we want the background
                    // blurred.
                    .hazeEffect(state = hazeState, style = HazeMaterials.ultraThin())
                    .fillMaxWidth(),
                title = {
                    Text("Hola")
                }
            )
        }
    }

}
