package com.dian.prueba.viewModel

import com.dian.prueba.repository.LoginRepo
import com.dian.prueba.utilities.Logger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import androidx.lifecycle.*
import com.dian.prueba.model.Tokens
import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.network.APIClient
import com.dian.prueba.utilities.TokenStorage
import com.dian.prueba.utilities.UpdateStorage

class LoginVM(
    private val loginRepo: LoginRepo = LoginRepo()
) : ViewModel() {

    private val logger = Logger()
    private val _tokens = MutableStateFlow<Tokens?>(null)
    val tokens: StateFlow<Tokens?> = _tokens
    val showUpdateDialog = MutableStateFlow(true)
    val updateAvailable = MutableStateFlow(true)
    val mustUpdate = MutableStateFlow(true)
    val currentVersion = MutableStateFlow("1.2")
    val newVersion = MutableStateFlow("1.3")
    val apiClient = APIClient()


    private fun getRandomString(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..10)
            .map { allowedChars.random() }
            .joinToString("")

    }

    fun loginUser(id: Int) {
        viewModelScope.launch {
            val access = loginRepo.login(id)
            access?.let {
                logger.warn("Haciendo login en LoginViewModel...", "LoginVM")

                val t = Tokens(accessToken = it, refreshToken = getRandomString())
                _tokens.value = t
                TokenStorage.saveTokens(t)
                logger.debug(t.toString(), "LoginVM")
            }
        }

    }

    fun loadSavedTokens() {
        _tokens.value = TokenStorage.loadTokens()
    }

    init {
        loadSavedUpdateInfo()
    }

    fun loadSavedUpdateInfo() {
        UpdateStorage.loadUpdateInfo()?.let { updateInfo ->
            currentVersion.value = updateInfo.currentVersion
            newVersion.value = updateInfo.newVersion
            updateAvailable.value = updateInfo.updateAvailable
            showUpdateDialog.value = updateInfo.updateAvailable && updateInfo.mustUpdate
        }
        apiClient.checkUpdateAvailable()
        logger.debug(apiClient.checkUpdateAvailable().toString(), "checkUpdateAvailable")
    }

    fun updateApp(){
        logger.warn("Updating app...", "updateApp")
        val updateInfo = apiClient.updateApp()
        showUpdateDialog.value = updateInfo.updateAvailable
        mustUpdate.value = updateInfo.mustUpdate
        newVersion.value = updateInfo.newVersion
        currentVersion.value = updateInfo.currentVersion
        logger.debug(updateInfo.toString(), "updateApp")
    }
}