package com.dian.prueba.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dian.prueba.model.AssetMediaType
import com.dian.prueba.modelNuFeed.AssetType
import com.dian.prueba.modelNuFeed.Feed
import com.dian.prueba.modelNuFeed.NuFeedUIModel
import com.dian.prueba.repository.FeedRepository
import com.dian.prueba.utilities.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NuFeedVM(
    private val nuFeedRepository: FeedRepository
) : ViewModel() {
    private val _feedItems = MutableStateFlow<List<NuFeedUIModel>>(emptyList())
    val feedItems: StateFlow<List<NuFeedUIModel>> = _feedItems
    private var nextIndex: Int = 0
    private var hasMore: Boolean = true
    private var isLoading: Boolean = false

    private val logger = Logger("NuFeedVM")

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading || !hasMore) return

        viewModelScope.launch {
            isLoading = true
            try {
                val response = nuFeedRepository.getNuFeed(nextIndex)
                val newItems = response.feed
                    .mapNotNull { it.toUIModel() }

                _feedItems.update { current ->
                    current + newItems
                }
                val rawCount = response.feed.size
                val mapped = response.feed.mapNotNull { it.toUIModel() }

                logger.warn("Total items: $rawCount, UI items: ${mapped.size}")
                logger.warn("New Items $newItems")
                logger.warn("Next index: ${response.next_index}, hasMore: ${response.has_more}")
                nextIndex = response.next_index
                hasMore = response.has_more

            } catch (e: Exception) {
                logger.error(e)
            } finally {
                isLoading = false
            }
        }
    }
}

/**
 * Como no mapeo los datos de la API en el servicio lo hago en otra función.
 * De esta forma dependiendo del tipo de asset que sea devuelve unos datos
 * u otros (en el caso de message solo texto y en el caso de tile más elementos)
 */
fun Feed.toUIModel(): NuFeedUIModel? {
    return when {

        type == AssetType.MESSAGE_IN -> {
            val actions = actions

            body?.let {
                NuFeedUIModel.MessageIn(
                    text = it,
                    actions = actions,
                )
            }
        }
        type == AssetType.MESSAGE_OUT ->{
            body?.let {
                NuFeedUIModel.MessageOut(text = it)
            }
        }
        type ==AssetType.TILE -> {
            val imageUrl = asset?.variants?.firstOrNull()?.url
            val posterUrl = asset?.posterVariants?.firstOrNull()?.url
            val productId = product?.product
            if (asset?.type == AssetMediaType.VIDEO){
                NuFeedUIModel.Tile(
                    imageUrl = posterUrl.toString(),
                    urlVideo = asset.url,
                    isPremium = isPremium ?: false,
                    isFavorite = isFavorite ?: false,
                    productId = productId.toString(),
                    typeMedia = AssetMediaType.VIDEO
                )
            } else {
                NuFeedUIModel.Tile(
                    imageUrl = imageUrl.toString(),
                    isPremium = isPremium ?: false,
                    isFavorite = isFavorite ?: false,
                    productId = productId.toString(),
                    typeMedia = AssetMediaType.IMAGE
                )
            }
        }
        else -> null
    }
}

