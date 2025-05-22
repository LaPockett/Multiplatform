package com.dian.prueba.utilities

import com.dian.prueba.logger
import com.dian.prueba.model.UpdateInfo
import com.russhwolf.settings.MapSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.*
import com.sun.tools.javac.util.Context
import java.util.prefs.Preferences

object UpdateStorage {
    //private const val UPDATE_AVAILABLE_KEY = "update_available"
    const val MUST_UPDATE_KEY = "must_update"
    const val NEW_VERSION_KEY = "new_version"
    const val CURRENT_VERSION_KEY = "current_version"
    //val settings = Settings()
    //private val settings : Settings = PreferencesSettings(Preferences.userRoot())

    private lateinit var _settings: Settings
    val settings: Settings = MapSettings(
        mutableMapOf(
            MUST_UPDATE_KEY to false,
            NEW_VERSION_KEY to "1.3",
            CURRENT_VERSION_KEY to "1.2"
        )
    )
    //val settings: Settings = MapSettings()
    /*fun init(settings: Settings) {
        _settings = settings
    }*/

    fun updateToNewVersion(newVersion: String): UpdateInfo {
        val updateInfo = UpdateInfo(
            mustUpdate = false,
            currentVersion = newVersion,
            newVersion = newVersion,
        )
        saveUpdateAvailable(updateInfo)
        logger.debug(updateInfo.toString(), "updateToNewVersion")
        return updateInfo
    }
    fun saveUpdateAvailable(updateInfo: UpdateInfo) {
        //settings[UPDATE_AVAILABLE_KEY] = updateInfo.updateAvailable
        settings[MUST_UPDATE_KEY] = updateInfo.mustUpdate
        settings[NEW_VERSION_KEY] = updateInfo.newVersion
        settings[CURRENT_VERSION_KEY] = updateInfo.currentVersion
        logger.debug(updateInfo.currentVersion.toString(), "saveUpdateAvailable")
    }


    fun loadUpdateInfo(): UpdateInfo? {
        //val updateAvailable = settings.getBooleanOrNull(UPDATE_AVAILABLE_KEY)
        val mustUpdate = settings.getBooleanOrNull(MUST_UPDATE_KEY)
        val newVersion = settings.getStringOrNull(NEW_VERSION_KEY)
        val currentVersion = settings.getStringOrNull(CURRENT_VERSION_KEY)
        if (currentVersion != null && newVersion != null) {
            logger.debug("$currentVersion - $newVersion", "loadUpdateInfo - UpdateStorage")
            return UpdateInfo(
                mustUpdate = mustUpdate ?: false,
                newVersion = newVersion,
                currentVersion = currentVersion
            )
        }
        return null
    }

}