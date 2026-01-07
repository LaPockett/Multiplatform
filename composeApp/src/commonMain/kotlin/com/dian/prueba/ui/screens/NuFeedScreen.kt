package com.dian.prueba.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dian.prueba.model.AssetMediaType
import com.dian.prueba.model.LocalColors
import com.dian.prueba.model.LocalPadding
import com.dian.prueba.modelNuFeed.NuFeedUIModel
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.dian.prueba.ui.components.CustomSearchBar
import com.dian.prueba.viewModel.NuFeedVM
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NuFeedScreen(
    viewModel: NuFeedVM,
    paddingValues: PaddingValues
) {
    val feedItems by viewModel.feedItems.collectAsState()
    val listState = rememberLazyGridState()
    val paddingModifier = LocalPadding.current
    val colorModifier = LocalColors.current

    Box(
        modifier = Modifier
            .background(colorModifier.backgroundApp)
            .fillMaxSize()
            //.windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = paddingModifier.tiny),//.statusBarsPadding()
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(paddingModifier.extraTiny),
            verticalArrangement = Arrangement.spacedBy(paddingModifier.extraTiny),
            modifier = Modifier
                .fillMaxSize(),
            state = listState
        )  {
            items(
                items = feedItems,
                span = { item ->
                    when (item) {
                        is NuFeedUIModel.MessageIn -> GridItemSpan(2)
                        is NuFeedUIModel.MessageOut -> GridItemSpan(2)
                        is NuFeedUIModel.Tile -> GridItemSpan(1)
                    }
                }
            ) { item ->
                when (item) {
                    is NuFeedUIModel.MessageIn -> MessageItem(item)
                    is NuFeedUIModel.MessageOut -> MessageItem(item)
                    is NuFeedUIModel.Tile -> TileItem(item)
                }
            }
        }
    }
    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItem =
                layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem to totalItems
        }.collect { (lastVisible, total) ->
            if (lastVisible >= total - 6) {
                viewModel.loadNextPage()
            }
        }
    }
}


@Composable
fun TileItem(item: NuFeedUIModel.Tile) {
    val paddingModifier = LocalPadding.current
    MultiplatformTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            /*Column(
                modifier = Modifier.fillMaxWidth().padding(paddingModifier.small),
                verticalArrangement = Arrangement.Center,
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(290.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(
                    modifier = Modifier.height(paddingModifier.small)
                )
                Text(
                    text = "Premium: ${item.isPremium}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = paddingModifier.extraTiny)
                )
                Text(
                    text = "Favorite: ${item.isFavorite}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = paddingModifier.extraTiny)
                )
                Text(
                    text = "Product: ${item.productId}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = paddingModifier.extraTiny)
                )*/
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

}

@Composable
fun MessageItem(item: NuFeedUIModel.MessageOut) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        CustomSearchBar(
            query = "",
            onQueryChange = {},
            placeholder = item.text,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MessageItem(item: NuFeedUIModel.MessageIn) {
    val paddingModifier = LocalPadding.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        CustomSearchBar(
            query = "",
            onQueryChange = {},
            placeholder = item.text,
            modifier = Modifier.fillMaxWidth()
        )
    }
}