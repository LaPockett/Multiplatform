package com.dian.prueba.utilities

import com.dian.prueba.model.UpdateInfo
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

object UpdateStorage {
    private const val UPDATE_AVAILABLE_KEY = "update_available"
    private const val MUST_UPDATE_KEY = "must_update"
    private const val NEEDS_UPDATE_KEY = "needs_update"
    private const val NEW_VERSION_KEY = "new_version"
    private const val CURRENT_VERSION_KEY = "current_version"
    val settings = Settings()

    fun updateToNewVersion(newVersion: String): UpdateInfo {
        val updateInfo = UpdateInfo(
            updateAvailable = false,
            mustUpdate = false,
            needsUpdate = false,
            currentVersion = newVersion,
            newVersion = newVersion,
        )
        saveUpdateAvailable(updateInfo)
        return updateInfo
    }
    fun saveUpdateAvailable(updateInfo: UpdateInfo) {
        settings[UPDATE_AVAILABLE_KEY] = updateInfo.updateAvailable
        settings[MUST_UPDATE_KEY] = updateInfo.mustUpdate
        settings[NEEDS_UPDATE_KEY] = updateInfo.needsUpdate
        settings[NEW_VERSION_KEY] = updateInfo.newVersion
        settings[CURRENT_VERSION_KEY] = updateInfo.currentVersion
    }


    fun loadUpdateInfo(): UpdateInfo? {
        val updateAvailable = settings.getBooleanOrNull(UPDATE_AVAILABLE_KEY)
        val mustUpdate = settings.getBooleanOrNull(MUST_UPDATE_KEY)
        val needsUpdate = settings.getBooleanOrNull(NEEDS_UPDATE_KEY)
        val newVersion = settings.getStringOrNull(NEW_VERSION_KEY)
        val currentVersion = settings.getStringOrNull(CURRENT_VERSION_KEY)
        if (updateAvailable != null) {
            return UpdateInfo(
                updateAvailable = updateAvailable,
                mustUpdate = mustUpdate ?: false,
                needsUpdate = needsUpdate ?: false,
                newVersion = newVersion ?: "1.3",
                currentVersion = currentVersion ?: "1.2"
            )
        }
        return null
    }

}