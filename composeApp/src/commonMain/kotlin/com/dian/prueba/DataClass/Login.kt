package com.dian.prueba.DataClass

import kotlinx.serialization.Serializable

@Serializable
data class Login(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)

