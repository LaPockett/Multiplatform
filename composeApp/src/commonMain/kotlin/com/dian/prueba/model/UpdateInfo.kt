package com.dian.prueba.model

data class UpdateInfo(
    //val updateAvailable: Boolean,
    val mustUpdate: Boolean,
    //val needsUpdate: Boolean,
    val currentVersion: String,
    val newVersion: String
){
    val updateAvailable : Boolean
        get () = currentVersion != newVersion
}
