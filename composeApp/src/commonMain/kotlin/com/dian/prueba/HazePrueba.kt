package com.dian.prueba

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.dian.prueba.ui.Theme.MultiplatformTheme
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.matcha
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Preview
@Composable
fun PruebaHaze() {
    val hazeState = rememberHazeState()

    MultiplatformTheme {
        Box {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 5.dp,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    items(50) { index ->
                        Image(
                            painter = painterResource(Res.drawable.matcha),
                            contentDescription = "description",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth().height(380.dp)
                        )
                    }
                },
                modifier = Modifier.fillMaxSize().hazeSource(hazeState)
            )
            TopAppBar(
                // Need to make app bar transparent to see the content behind
                colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
                modifier = Modifier
                    // We use hazeEffect on anything where we want the background
                    // blurred.
                    .hazeEffect(
                        state = hazeState,
                        style = HazeMaterials.ultraThin()
                    ){
                        progressive = HazeProgressive.verticalGradient(startIntensity = 0.5f, endIntensity = 0f)
                    }
                    .fillMaxWidth().padding(WindowInsets.safeDrawing.asPaddingValues()),
                title = {
                    Text("Matcha")
                },
                windowInsets = WindowInsets(
                    top = 0,
                    bottom = 0
                ),
                navigationIcon =
                    {
                        IconDefaultCustom(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.DarkGray
                        )
                    }
            )
        }
    }
}

@Composable
fun IconDefaultCustom(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    tint : Color = MaterialTheme.colorScheme.primary
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(32.dp)
            .padding(4.dp),
        tint = tint
    )
}