package com.dian.prueba.viewModel

import androidx.lifecycle.ViewModel
import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.network.APIClient
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.UpdateStorage
import kotlinx.coroutines.flow.MutableStateFlow

class UpdateVM (
    private val apiClient : APIClient = APIClient()
) : ViewModel() {

    private val _updateInfo = MutableStateFlow<UpdateInfo?>(null)
    val updateInfo: MutableStateFlow<UpdateInfo?> = _updateInfo
    private val logger = Logger()
    val showUpdateDialog = MutableStateFlow(false)

    init {
        loadSavedUpdateInfo()
    }

    private fun loadSavedUpdateInfo() {
        val savedInfo = UpdateStorage.loadUpdateInfo()
        logger.debug(savedInfo.toString(), "loadSavedUpdateInfo - UpdateVM")
        UpdateStorage.loadUpdateInfo()?.let {
            _updateInfo.value = it
            showUpdateDialog.value = it.updateAvailable && it.mustUpdate
        }

    }

    fun checkForUpdates(){
        val updateInfo = apiClient.checkUpdateAvailable()
        _updateInfo.value = updateInfo
        UpdateStorage.saveUpdateAvailable(updateInfo)
        showUpdateDialog.value = updateInfo.updateAvailable && updateInfo.mustUpdate
        logger.debug(updateInfo.toString(), "checkForUpdates - UpdateVM")
    }

    fun doUpdate(){
        _updateInfo.value?.let { currentInfo ->
            val updatedInfo = UpdateStorage.updateToNewVersion(currentInfo.newVersion)
            _updateInfo.value = updatedInfo
            showUpdateDialog.value = false
        }
    }

}