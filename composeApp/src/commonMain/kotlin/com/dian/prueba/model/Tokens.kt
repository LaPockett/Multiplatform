package com.dian.prueba.model

data class Tokens (
    val accessToken: String,
    val refreshToken: String?= null
)