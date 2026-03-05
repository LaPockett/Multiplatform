package com.dian.prueba.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FloatingActionButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.dian.prueba.data.globalResources.LocalColors
import com.dian.prueba.data.globalResources.LocalPadding
import com.dian.prueba.domain.nuFeed.model.NuFeedUIModel
import com.dian.prueba.viewModel.FeedVM
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import com.dian.prueba.liquidglass.components.CarouselHorizontalSample
import com.dian.prueba.ui.components.buttons.SlideToBookButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetBag(
    isSheetOpen: Boolean,
    onDismissRequest: () -> Unit,
    state: SheetState,
    selected: NuFeedUIModel.Tile?,
    productId: String,
    feedViewModel: FeedVM
) {
    val paddingModifier = LocalPadding.current
    val colorModifier = LocalColors.current
    feedViewModel.loadProductDetail(productId)
    val product = feedViewModel.productDetail.collectAsState()
    var expandedImageUrl by remember { mutableStateOf<String?>(null) }
    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = state,
            containerColor = colorModifier.backgroundApp,
            dragHandle = {
                BottomSheetDefaults.DragHandle()
            },
            sheetGesturesEnabled = false
        ) {
            Box(
                modifier = Modifier.fillMaxSize().background(colorModifier.backgroundApp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    //val pictures = product.value?.variants?.firstOrNull()?.pictures ?: emptyList()
                    val allImageVariants = product.value?.variants?.firstOrNull()?.pictures
                        ?.flatMap { picture -> picture.variants }
                        ?: emptyList()
                    Box(
                        modifier = Modifier.fillMaxWidth().height(650.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Para cargar solo el primer elemento de los variant dentro de pictures
                            /*items(pictures,
                            ){ picture ->
                                val imageUrl = picture.variants.firstOrNull()?.url
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = selected?.typeMedia.toString(),
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }*/
                            // Carga todos los elementos de los variant dentro de pictures
                            items(allImageVariants) { variant ->
                                ZoomableImage(
                                    imageUrl = variant.url,
                                    onExpand = {
                                        expandedImageUrl = it
                                    }
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .align(Alignment.BottomCenter)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colorStops = arrayOf(
                                            0.0f to Color.Transparent,
                                            0.3f to colorModifier.backgroundApp.copy(alpha = 0.4f),
                                            0.6f to colorModifier.backgroundApp.copy(alpha = 0.7f),
                                            1.0f to colorModifier.backgroundApp
                                        )
                                    )
                                )
                        )
                    }
                    Spacer(modifier = Modifier.height(paddingModifier.small))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = paddingModifier.small)
                    ) {
                        Text(
                            // Para sustituir el '_' de la cadena por un espacio
                            text = product.value?.brand?.replace("_", " ") ?: "nil",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                        Text(
                            text = feedViewModel.productDetail.value?.productName ?: "nil",
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
                            letterSpacing = 0.4.sp,
                            modifier = Modifier.padding(bottom = paddingModifier.extraTiny)
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
                                text = feedViewModel.productDetail.value?.storyTelling ?: "nil",
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
                                letterSpacing = 0.4.sp,
                                modifier = Modifier.padding(bottom = paddingModifier.extraTiny)
                            )
                            Text(
                                text = feedViewModel.productDetail.value?.styleIt ?: "nil",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.padding(vertical = paddingModifier.tiny))
                            Text(
                                text = "YOURS NEXT",
                                style = MaterialTheme.typography.labelLarge,
                                color = colorModifier.logoColor,
                                letterSpacing = 0.4.sp,
                                modifier = Modifier.padding(bottom = paddingModifier.extraTiny)
                            )
                        }
                    }
                    CarouselCalendar()
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = paddingModifier.small)
                    ) {
                        SlideToBookButton(
                            btnText = "Slide to receive it",
                            outerBtnBackgroundColor = colorModifier.logoColorLight,
                            sliderBtnBackgroundColor = colorModifier.logoColor,
                            onBtnSwipe = {
                                println("Slide to book button swiped!")
                            },
                        )
                        Spacer(modifier = Modifier.padding(vertical = paddingModifier.tiny))
                        Text(
                            text = "SIMILAR BAGS",
                            style = MaterialTheme.typography.labelLarge,
                            color = colorModifier.logoColor,
                            letterSpacing = 0.4.sp,
                            modifier = Modifier.padding(bottom = paddingModifier.extraTiny)
                        )
                        CarouselHorizontalSample()
                        Spacer(modifier = Modifier.padding(vertical = paddingModifier.normal))
                    }
                }
                FloatingActionButton(
                    onClick = {
                        println("Floating action button clicked!")
                        //TODO
                    },
                    modifier = Modifier.padding(paddingModifier.tiny)
                        .align(Alignment.BottomEnd),
                    backgroundColor = colorModifier.logoColor,
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite Bag"
                    )
                }
                if (expandedImageUrl != null) {
                    ExpandedImageDialog(
                        imageUrl = expandedImageUrl!!,
                        onDismiss = {
                            expandedImageUrl = null
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ZoomableImage(
    imageUrl: String,
    onExpand: (String) -> Unit
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Zoomable Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onExpand(imageUrl)
                    },
                    onDoubleTap = {
                        onExpand(imageUrl)
                    }
                )
            }
    )
}