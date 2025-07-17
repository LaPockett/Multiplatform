package com.dian.prueba.viewModel

import androidx.lifecycle.ViewModel
import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.network.ApiService
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.UpdateStorage
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * ViewModel para manejar la lógica del diálogo de actualización de la aplicación,
 * y que además se comunica con UpdateStorage.
 */
class UpdateVM (
    private val updateStorage: UpdateStorage,
    private val apiService: ApiService
) : ViewModel() {

    private val _updateInfo = MutableStateFlow<UpdateInfo?>(null)
    val updateInfo: MutableStateFlow<UpdateInfo?> = _updateInfo
    private val logger = Logger("UpdateVM")
    val showUpdateDialog = MutableStateFlow(false)

    init {
        loadSavedUpdateInfo()
    }
    fun loadSavedUpdateInfo() {
        val savedInfo = updateStorage.loadUpdateInfo()
        logger.debug("loadSavedUpdateInfo: $savedInfo")
        updateStorage.loadUpdateInfo()?.let {
            _updateInfo.value = it
            showUpdateDialog.value = it.updateAvailable && it.mustUpdate
        }
    }

    fun checkForUpdates(){
        val updateInfo = apiService.checkUpdateAvailable()
        _updateInfo.value = updateInfo
        updateStorage.saveUpdateAvailable(updateInfo)
        showUpdateDialog.value = updateInfo.updateAvailable && updateInfo.mustUpdate
        logger.debug("checkForUpdates: $updateInfo")
    }

    fun doUpdate(){
        _updateInfo.value?.let { currentInfo ->
            val updatedInfo = updateStorage.updateToNewVersion(currentInfo.newVersion)
            _updateInfo.value = updatedInfo
            showUpdateDialog.value = false
        }
    }

}