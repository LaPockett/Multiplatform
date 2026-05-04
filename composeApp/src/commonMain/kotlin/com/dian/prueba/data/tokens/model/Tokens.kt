package com.dian.prueba.data.tokens.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tokens(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expiration")   val expiration: String?  = null,
    val refreshToken: String? = null
)