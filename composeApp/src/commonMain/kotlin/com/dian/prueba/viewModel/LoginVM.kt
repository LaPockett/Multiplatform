package com.dian.prueba.viewModel

import com.dian.prueba.repository.LoginRepo
import com.dian.prueba.utilities.Logger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import androidx.lifecycle.*
import com.dian.prueba.model.Tokens
import com.dian.prueba.network.APIClient
import com.dian.prueba.utilities.TokenStorage
import com.dian.prueba.utilities.UpdateStorage

class LoginVM(
    private val loginRepo: LoginRepo = LoginRepo()
) : ViewModel() {

    private val logger = Logger()
    private val _tokens = MutableStateFlow<Tokens?>(null)
    val tokens: StateFlow<Tokens?> = _tokens
    val showUpdateDialog = MutableStateFlow(false)
    val mustUpdate = MutableStateFlow(false)
    val apiClient = APIClient()


    private fun getRandomString() : String {
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
    fun checkForUpdates(){
        viewModelScope.launch {
            val update = apiClient.checkUpdateAvailable()
            val updateDone = UpdateStorage.getUpdateDone()?.updateDone == true
            if (update.updateAvailable && !updateDone){
                showUpdateDialog.value = true
                mustUpdate.value = update.mustUpdate
            }
        }
    }

}

/**
 * Clase para manejar el login
 */
/*class LoginVM (
    private val loginRepo: LoginRepo = LoginRepo(),
): ViewModel() {
    private val logger = Logger()
    private val _loginToken = MutableStateFlow<String?>("")
    val loginToken : StateFlow<String?> = _loginToken

    fun loginUser (id: Int) {
        viewModelScope.launch {
            logger.warn("Haciendo login en LoginViewModel...", "LoginVM")
            val token = loginRepo.login(id)
            _loginToken.value = token
        }
    }
}*/
