package com.dian.prueba.modelNuFeed

import kotlinx.serialization.Serializable

@Serializable
enum class TypeAsset() {
    TILE,
    MESSAGE_IN,
    MESSAGE_OUT,
}

@Serializable
enum class Mood() {
    CHILL,
    EVENTFUL
}