package com.dian.prueba.modelNuFeed

import kotlinx.serialization.Serializable

@Serializable
enum class AssetType() {
    TILE,
    MESSAGE_IN,
    MESSAGE_OUT,
}