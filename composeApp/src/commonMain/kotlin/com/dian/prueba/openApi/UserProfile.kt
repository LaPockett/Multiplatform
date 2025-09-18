package com.dian.prueba.openApi
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    @SerialName("username")
    val username: String,
    @SerialName("email")
    val email: String,
)