package com.dian.prueba.data.nuFeed.enums

import kotlinx.serialization.Serializable

@Serializable
enum class AssetType() {
    TILE,
    MESSAGE_IN,
    MESSAGE_OUT,
}