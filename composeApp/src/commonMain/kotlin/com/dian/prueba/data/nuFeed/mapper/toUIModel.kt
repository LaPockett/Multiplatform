package com.dian.prueba.data.nuFeed.mapper

import com.dian.prueba.data.feed.enums.AssetMediaType
import com.dian.prueba.data.nuFeed.enums.AssetType
import com.dian.prueba.data.nuFeed.model.Feed

/**
 * Como no mapeo los datos de la API en el servicio lo hago en otra función.
 * De esta forma dependiendo del tipo de asset que sea devuelve unos datos
 * u otros (en el caso de message solo texto y en el caso de tile más elementos)
 */

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
