package com.dian.prueba.data.model

data class UpdateInfo(
    val mustUpdate: Boolean,
    val currentVersion: String,
    val newVersion: String
){
    val updateAvailable : Boolean
    get () = currentVersion != newVersion
}
