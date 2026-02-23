package com.dian.prueba.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dian.prueba.data.model.AssetMediaType
import com.dian.prueba.data.model.LocalColors
import com.dian.prueba.data.model.LocalPadding
import com.dian.prueba.data.modelNuFeed.NuFeedUIModel
import com.dian.prueba.ui.components.CustomSearchBar
import com.dian.prueba.ui.components.HeaderFeedLogo
import com.dian.prueba.ui.components.ModalBottomSheetBag
import com.dian.prueba.utilities.FeatureFlagsManager
import com.dian.prueba.viewModel.FeedVM
import com.dian.prueba.viewModel.NuFeedVM
import io.github.kdroidfilter.composemediaplayer.VideoPlayerSurface
import io.github.kdroidfilter.composemediaplayer.rememberVideoPlayerState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ClosetScreen(
    paddingValues: PaddingValues,
    isAnimatedFinished: Boolean,
    feedVM: FeedVM,
    nuFeedVM: NuFeedVM
) {
    nuFeedVM.loadFeatureFlags()
    val feedItems by nuFeedVM.feedItems.collectAsState()
    val listState = rememberLazyGridState()
    val paddingModifier = LocalPadding.current
    val colorModifier = LocalColors.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            it != SheetValue.PartiallyExpanded
        }
    )
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    var itemSelected: NuFeedUIModel.Tile? by remember { mutableStateOf(null) }
    /**
     * Cada vez que cambiemos de screen del navigation se hará update de las flags
     * Al matar la app también, claro
     */
    val featureFlags by nuFeedVM.featureFlags.collectAsState()
    FeatureFlagsManager.update(featureFlags)
    val isFeatureEnabled: Boolean = FeatureFlagsManager.getData("videosInFeed")
    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem to totalItems
        }.collect { (lastVisible, total) ->
            if (lastVisible >= total - 9) {
                nuFeedVM.loadNextPage()
            }
        }
    }
    Box(
        modifier = Modifier.background(colorModifier.backgroundApp).fillMaxSize()
            //.windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = paddingModifier.tiny),//.statusBarsPadding()
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(paddingModifier.extraTiny),
            verticalArrangement = Arrangement.spacedBy(paddingModifier.extraTiny),
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            item(
                span = { GridItemSpan(2) }) {
                HeaderFeedLogo()
            }
            items(
                items = feedItems, span = { item ->
                    when (item) {
                        is NuFeedUIModel.MessageIn -> GridItemSpan(2)
                        is NuFeedUIModel.MessageOut -> GridItemSpan(2)
                        is NuFeedUIModel.Tile -> GridItemSpan(1)
                    }
                }) { item ->
                when (item) {
                    is NuFeedUIModel.MessageIn -> MessageItem(item)
                    is NuFeedUIModel.MessageOut -> MessageItem(item)
                    is NuFeedUIModel.Tile -> TileItem(
                        item,
                        onItemClick = { selectedItem ->
                            // Handle item click
                            itemSelected = selectedItem
                            if (isAnimatedFinished) {
                                isSheetOpen = true
                            }
                        }, setVideosInFeed = isFeatureEnabled
                    )
                }
            }
        }
    }
    if (isSheetOpen && itemSelected != null) {
        val productId = itemSelected!!.productId
        ModalBottomSheetBag(
            isSheetOpen = isSheetOpen,
            onDismissRequest = {
                isSheetOpen = false
            },
            state = sheetState,
            productId = productId,
            feedViewModel = feedVM,
            selected = itemSelected
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TileItem(
    item: NuFeedUIModel.Tile, onItemClick: (item: NuFeedUIModel.Tile) -> Unit,
    setVideosInFeed: Boolean = false
) {
    val playerState = rememberVideoPlayerState()
    val url = item.urlVideo.toString()
    Card(
        modifier = Modifier.fillMaxWidth().height(290.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        onClick = {
            println("TileItem clicked: ${item.productId}")
            onItemClick(item)
        }) {
        if (item.typeMedia != AssetMediaType.IMAGE && !setVideosInFeed) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.typeMedia.toString(),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else if (item.typeMedia != AssetMediaType.IMAGE && setVideosInFeed) {
            //println("URL VIDEO: $url de item: ${item.productId}")
            playerState.openUri(url)
            playerState.volume = 0f
            playerState.loop = true
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                VideoPlayerSurface(
                    playerState = playerState,
                    modifier = Modifier.fillMaxWidth().height(290.dp),
                    contentScale = ContentScale.Crop,
                    overlay = {
                        if (playerState.isLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = item.imageUrl,
                                    contentDescription = "Poster Video Preview",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                )
            }

        } else if (item.typeMedia == AssetMediaType.IMAGE) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.typeMedia.toString(),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
    /**
     * Need to fix
     * Issue: Video overlay another video
     * Web: https://github.com/Chaintech-Network/ComposeMultiplatformMediaPlayer/issues/187
     *//*val playerHost = remember {
                MediaPlayerHost(
                    mediaUrl = item.urlVideo.toString(),
                    isMuted = true,
                    initialVideoFitMode = ScreenResize.FILL
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                VideoPlayerComposable(
                    modifier = Modifier.fillMaxSize(),
                    playerHost = playerHost,
                    playerConfig = VideoPlayerConfig(
                        showControls = false,
                        isSeekBarVisible = false,
                        isZoomEnabled = false,
                        loadingIndicatorColor = Color.Transparent,
                        isDurationVisible = false,
                        reelVerticalScrolling = false,
                        loaderView = {
                            AsyncImage(
                                model = item.imageUrl,
                                contentDescription = "Poster Video Preview",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        },
                        controlClickAnimationDuration = 0,
                        controlHideIntervalSeconds = 0,
                    )
                )
            }*/

}

//val currentLanguage = Locale.current.language

@Composable
fun MessageItem(item: NuFeedUIModel.MessageOut) {
    val paddingModifier = LocalPadding.current

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = paddingModifier.extraTiny),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomSearchBar(
            query = "", onQueryChange = {},
            //placeholder = TranslationManager.translate(item.text, currentLanguage),
            placeholder = item.text, modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MessageItem(item: NuFeedUIModel.MessageIn) {
    val paddingModifier = LocalPadding.current
    //print("Current language: $currentLanguage")
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = paddingModifier.extraTiny),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomSearchBar(
            query = "",
            onQueryChange = {},
            //placeholder = TranslationManager.translate(item.text, currentLanguage),
            placeholder = item.text,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}