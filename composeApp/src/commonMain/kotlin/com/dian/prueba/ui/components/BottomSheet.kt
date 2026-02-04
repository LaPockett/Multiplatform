package com.dian.prueba.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.dian.prueba.model.LocalColors
import com.dian.prueba.model.LocalPadding
import com.dian.prueba.modelNuFeed.NuFeedUIModel
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.matcha
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Composables de prueba/práctica
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheetSample() {
    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    //val scope = rememberCoroutineScope()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                isSheetOpen = true
            }
        ) {
            Text(
                text = "Expand the bottom sheet"
            )
        }
    }
    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                isSheetOpen = false
            },
            sheetState = state,
        ) {
            LazyColumn {
                items(2) {
                    ListItem(
                        text = { Text("Bag $it") },
                        icon = {
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

//@Preview
/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWithBottomSheetSample(
    isSheetOpen: Boolean
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                isSheetOpen = true
            }
        ){
            Text(
                text = "Expand the bottom sheet"
            )
        }
    }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetBag(
    isSheetOpen: Boolean,
    onDismissRequest: () -> Unit,
    state: SheetState,
    selected: NuFeedUIModel.Tile?
) {
    val paddingModifier = LocalPadding.current
    val colorModifier = LocalColors.current
    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = state,
            containerColor = colorModifier.backgroundApp,
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = selected!!.imageUrl,
                    contentDescription = selected.typeMedia.toString(),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(paddingModifier.small))
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = paddingModifier.small)
                ) {
                    Text(
                        text = "BOTTEGA VENETA",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    Text(
                        text = "Foulard | Medium Intreccio",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Light,
                        color = colorModifier.blackLight
                    )
                    Spacer(modifier = Modifier.height(paddingModifier.tiny))
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = colorModifier.dividerLight
                    )
                    Spacer(modifier = Modifier.height(paddingModifier.normal))
                    Text(
                        text = "OVERVIEW",
                        style = MaterialTheme.typography.labelLarge,
                        color = colorModifier.logoColor,
                        letterSpacing = 0.4.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .height(IntrinsicSize.Min),
                    ) {
                        VerticalDivider(
                            color = colorModifier.logoColor,
                            modifier = Modifier.fillMaxHeight().width(1.dp)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = paddingModifier.extraTiny))
                        Text(
                            text = "Lorem ipsum dolor sit amet consectetur adipiscing elit curae placerat, dapibus lacus facilisi cras vivamus pretium viverra nec, vel arcu magna orci ridiculus fames volutpat quis. Aliquam parturient ac etiam habitasse vehicula ornare sociis lobortis, commodo nostra habitant ridiculus quam litora imperdiet, suscipit erat donec turpis luctus malesuada curae.",
                            modifier = Modifier.padding(bottom = paddingModifier.tiny),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Spacer(modifier = Modifier.padding(vertical = paddingModifier.extraTiny))
                        Text(
                            text = "STYLING TIPS",
                            style = MaterialTheme.typography.labelLarge,
                            color = colorModifier.logoColor,
                            letterSpacing = 0.4.sp
                        )
                        Text(
                            text = "-",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.padding(vertical = paddingModifier.extraTiny))
                        Text(
                            text = "YOURS NEXT",
                            style = MaterialTheme.typography.labelLarge,
                            color = colorModifier.logoColor,
                            letterSpacing = 0.4.sp
                        )
                        CarouselCalendar()
                    }
                }
            }
        }
    }
}