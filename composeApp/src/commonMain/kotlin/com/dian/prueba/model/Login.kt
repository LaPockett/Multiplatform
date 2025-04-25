package com.dian.prueba.model

import kotlinx.serialization.Serializable

@Serializable
data class Login(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)

