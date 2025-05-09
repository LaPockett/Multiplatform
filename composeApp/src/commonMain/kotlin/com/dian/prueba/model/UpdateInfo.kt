package com.dian.prueba.model

data class UpdateInfo(
    val updateAvailable: Boolean,
    val mustUpdate: Boolean
)
data class UpdateInfoUser(
    val updateDone: Boolean
)