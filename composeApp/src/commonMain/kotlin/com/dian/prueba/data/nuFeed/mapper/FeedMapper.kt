package com.dian.prueba.data.nuFeed.mapper

import com.dian.prueba.data.feed.enums.AssetMediaType
import com.dian.prueba.data.feed.model.FeedItemResponse
import com.dian.prueba.domain.feed.model.FeedItemUI
import com.dian.prueba.domain.feed.model.ProductUIModel
import com.dian.prueba.data.nuFeed.enums.AssetType
import com.dian.prueba.data.nuFeed.model.Feed
import com.dian.prueba.domain.nuFeed.model.NuFeedUIModel

fun FeedItemResponse.toProductUIModel(): ProductUIModel? {
    return when (asset.type) {
        AssetMediaType.IMAGE -> {
            val imageUrl = asset.variants?.firstOrNull()?.url ?: return null
            ProductUIModel(
                imageUrl = imageUrl,
                assetType = AssetMediaType.IMAGE,
                feedItem = toFeedItemUI(),
                productId = product.product
            )
        }
        AssetMediaType.VIDEO -> {
            val posterUrl = asset.posterVariants?.firstOrNull()?.url ?: return null
            ProductUIModel(
                imageUrl = posterUrl,
                urlVideo = asset.url,
                assetType = AssetMediaType.VIDEO,
                feedItem = toFeedItemUI(),
                productId = product.product
            )
        }
    }
}

private fun FeedItemResponse.toFeedItemUI(): FeedItemUI {
    return FeedItemUI(
        isPremium = isPremium,
        isFavorite = isFavorite,
        assetUrl = asset.variants?.firstOrNull()?.url
            ?: asset.posterVariants?.firstOrNull()?.url
            ?: "",
        productId = product.product,
        variantId = product.variant
    )
}

fun Feed.toUIModel(): NuFeedUIModel? {
    return when (type) {
        AssetType.MESSAGE_IN -> {
            val actions = actions

            body?.let {
                NuFeedUIModel.MessageIn(
                    text = it,
                    actions = actions,
                )
            }
        }
        AssetType.MESSAGE_OUT -> {
            body?.let {
                NuFeedUIModel.MessageOut(text = it)
            }
        }
        AssetType.TILE -> {
            val posterUrl = asset?.posterVariants?.firstOrNull()?.url
            if (asset?.type == AssetMediaType.VIDEO){
                NuFeedUIModel.Tile(
                    imageUrl = posterUrl.toString(),
                    urlVideo = asset.url,
                    isPremium = isPremium ?: false,
                    isFavorite = isFavorite ?: false,
                    productId = product?.product ?: return null,
                    typeMedia = AssetMediaType.VIDEO
                )
            } else {
                NuFeedUIModel.Tile(
                    imageUrl = asset?.variants?.firstOrNull()?.url ?: return null,
                    isPremium = isPremium ?: false,
                    isFavorite = isFavorite ?: false,
                    productId = product?.product ?: return null,
                    typeMedia = AssetMediaType.IMAGE
                )
            }
        }
    }
}
