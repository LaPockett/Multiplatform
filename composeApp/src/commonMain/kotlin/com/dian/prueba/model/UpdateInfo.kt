package com.dian.prueba.model

data class UpdateInfo(
    val mustUpdate: Boolean,
    val currentVersion: String,
    val newVersion: String
){
    val updateAvailable : Boolean
    get () = currentVersion != newVersion
}
