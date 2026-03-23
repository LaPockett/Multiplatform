package com.dian.prueba.ui.screens.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.dian.prueba.data.feed.enums.AssetMediaType
import com.dian.prueba.data.globalResources.LocalColors
import com.dian.prueba.data.globalResources.LocalDimension
import com.dian.prueba.data.globalResources.LocalPadding
import com.dian.prueba.domain.nuFeed.model.NuFeedUIModel
import com.dian.prueba.liquidglass.destinations.BottomTabsLiquidGlass
import com.dian.prueba.liquidglass.destinations.GlassClippyLogo
import com.dian.prueba.ui.components.CustomSearchBar
import com.dian.prueba.ui.components.HeaderFeedLogo
import com.dian.prueba.ui.components.ModalBottomSheetBag
import com.dian.prueba.viewModel.FeedVM
import com.dian.prueba.viewModel.NuFeedVM
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState
import io.github.kdroidfilter.composemediaplayer.VideoPlayerSurface
import io.github.kdroidfilter.composemediaplayer.rememberVideoPlayerState
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.logo
import multiplatform.composeapp.generated.resources.logotitle
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ClosetScreen(
    navController: NavHostController,
    isAnimatedFinished: Boolean,
    feedVM: FeedVM,
    nuFeedVM: NuFeedVM,
    navigateTo: (route: String) -> Unit
) {
    val uiDimensions = LocalDimension.current
    val hazeState = rememberHazeState()
    val lightAlpha = 0.3f
    val darkAlpha = 0.1f
    val hazeStyle = HazeStyle(
        backgroundColor = Color.White,
        tints = listOf(
            HazeTint(
                Color.White.copy(alpha = if (Color.White.luminance() >= 0.5) lightAlpha else darkAlpha),
            )
        ),
        blurRadius = 5.dp,
        noiseFactor = -1f,
        fallbackTint = HazeTint.Unspecified,
    )
    val backdrop = rememberLayerBackdrop()

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

    //* Feature flag values update every 10 seconds, and the ViewModel is cleared when the activity is killed.
    val featureFlags by nuFeedVM.featureFlags.collectAsState()
    val isFeatureEnabled = featureFlags["videosInFeed"] ?: false
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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .hazeEffect(state = hazeState, style = hazeStyle)
                    .fillMaxWidth()
                    .dropShadow(
                        shape = RoundedCornerShape(20.dp),
                        shadow = Shadow(
                            radius = 8.dp,
                            spread = 3.dp,
                            color = Color(0xd7ffffff),
                            offset = DpOffset(x = 4.dp, 4.dp)
                        )
                    )
                    .height(95.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorModifier.backgroundApp,
                                colorModifier.backgroundApp.copy(alpha = 0.9f),
                                colorModifier.backgroundApp.copy(alpha = 1f),
                                colorModifier.backgroundApp.copy(alpha = 0.8f),
                                colorModifier.backgroundApp.copy(alpha = 0.7f),
                                colorModifier.backgroundApp.copy(alpha = 0.6f),
                                colorModifier.backgroundApp.copy(alpha = 0.5f),
                                colorModifier.backgroundApp.copy(alpha = 0.4f),
                                colorModifier.backgroundApp.copy(alpha = 0.3f),
                                colorModifier.backgroundApp.copy(alpha = 0.1f),
                                colorModifier.backgroundApp.copy(alpha = 0f),
                            ),
                            startY = 245f,
                            endY = 300f,
                        )
                    ),
                windowInsets = WindowInsets.statusBars,
                navigationIcon = {},//
                actions = {},
                title = {
                    ImageLogo(
                        tint = Color.Black,
                        painter = painterResource(Res.drawable.logotitle),
                        modifier = Modifier.height(48.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),

                )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        floatingActionButton = {
            GlassClippyLogo(
                onClick = {},
                backdrop = backdrop,
                surfaceColor = Color.DarkGray.copy(0.2f),
                sizeClippy = 50.dp
            ) {
                Icon(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(uiDimensions.iconNormal)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = Color.Transparent,
        bottomBar = {
            //GlassmorphicBottomNavigation(hazeState, navController)
            Box(
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                BottomTabsLiquidGlass(backdrop, navController)
            }

        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop)
                .background(colorModifier.backgroundApp)
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
            selected = itemSelected,
            onConfirmOrder = {
                //isSheetOpen = false
                navigateTo("closet/confirmOrder")
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TileItem(
    item: NuFeedUIModel.Tile,
    onItemClick: (item: NuFeedUIModel.Tile) -> Unit,
    setVideosInFeed: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth().height(290.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        onClick = { onItemClick(item) }
    ) {
        when {
            item.typeMedia == AssetMediaType.IMAGE -> {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.typeMedia.toString(),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            setVideosInFeed -> {
                val playerState = rememberVideoPlayerState()
                val url = item.urlVideo ?: return@Card

                LaunchedEffect(url) {
                    playerState.openUri(url)
                    playerState.volume = 0f
                    playerState.loop = true
                }
                VideoPlayerSurface(
                    playerState = playerState,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    overlay = {
                        if (playerState.isLoading) {
                            AsyncImage(
                                model = item.imageUrl,
                                contentDescription = "Poster Video Preview",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                )
            }

            else -> {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.typeMedia.toString(),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    //! Need to fix
    //! Issue: Video overlay another video
    //ref: https://github.com/Chaintech-Network/ComposeMultiplatformMediaPlayer/issues/187
    /*val playerHost = remember {
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