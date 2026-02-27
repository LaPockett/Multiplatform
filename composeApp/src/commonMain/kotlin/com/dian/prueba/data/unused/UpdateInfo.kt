package com.dian.prueba.data.unused

data class UpdateInfo(
    val mustUpdate: Boolean,
    val currentVersion: String,
    val newVersion: String
){
    val updateAvailable : Boolean
    get () = currentVersion != newVersion
}
