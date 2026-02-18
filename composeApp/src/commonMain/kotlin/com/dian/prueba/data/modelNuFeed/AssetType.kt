package com.dian.prueba.data.modelNuFeed

import kotlinx.serialization.Serializable

@Serializable
enum class AssetType() {
    TILE,
    MESSAGE_IN,
    MESSAGE_OUT,
}