package com.dian.prueba.utilities

import com.dian.prueba.model.UpdateInfoUser
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

object UpdateStorage {
    private const val UPDATE_DONE_KEY = "update_done"
    private val settings = Settings()

    fun setUpdateDone(updateDone: UpdateInfoUser) {
        settings[UPDATE_DONE_KEY] = updateDone.updateDone
    }

    fun getUpdateDone(): UpdateInfoUser? {
        val updateDone = settings.getBooleanOrNull(UPDATE_DONE_KEY) ?: return null
        return UpdateInfoUser(updateDone)
    }
}