package com.dian.prueba.utilities

import com.dian.prueba.data.model.UpdateInfo
import com.russhwolf.settings.Settings
import com.russhwolf.settings.*

interface UpdateStorage {
    fun saveUpdateAvailable(updateInfo: UpdateInfo)
    fun loadUpdateInfo(): UpdateInfo?
    fun updateToNewVersion(newVersion: String): UpdateInfo
    fun clear()
}

class UpdateStorageImpl(private val settings: Settings) : UpdateStorage {
    private companion object {
        const val MUST_UPDATE_KEY = "must_update"
        const val NEW_VERSION_KEY = "new_version"
        const val CURRENT_VERSION_KEY = "current_version"
    }

    init {
        if (settings.getStringOrNull(CURRENT_VERSION_KEY) == null) {
            settings[CURRENT_VERSION_KEY] = "1.2"
        }
    }
    override fun saveUpdateAvailable(updateInfo: UpdateInfo) {
        settings[MUST_UPDATE_KEY] = updateInfo.mustUpdate
        settings[NEW_VERSION_KEY] = updateInfo.newVersion
        settings[CURRENT_VERSION_KEY] = updateInfo.currentVersion
    }

    override fun loadUpdateInfo(): UpdateInfo? {
        val mustUpdate = settings.getBooleanOrNull(MUST_UPDATE_KEY)
        val newVersion = settings.getStringOrNull(NEW_VERSION_KEY)
        val currentVersion = settings.getStringOrNull(CURRENT_VERSION_KEY)

        return if (currentVersion != null && newVersion != null) {
            UpdateInfo(
                mustUpdate = mustUpdate ?: false,
                newVersion = newVersion,
                currentVersion = currentVersion
            )
        } else {
            null
        }
    }
    override fun updateToNewVersion(newVersion: String): UpdateInfo {
        val updateInfo = UpdateInfo(
            mustUpdate = false,
            currentVersion = newVersion,
            newVersion = newVersion
        )
        saveUpdateAvailable(updateInfo)
        return updateInfo
    }

    override fun clear() {
        settings.remove(MUST_UPDATE_KEY)
        settings.remove(NEW_VERSION_KEY)
        settings.remove(CURRENT_VERSION_KEY)
    }
}