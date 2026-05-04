package com.dian.prueba.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dian.prueba.data.tokens.model.Tokens
import com.dian.prueba.repository.LoginRepository
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserVM(
    private val loginRepository: LoginRepository,
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val _tokensInfo = MutableStateFlow<Tokens?>(null)
    val tokensInfo: StateFlow<Tokens?> = _tokensInfo

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val logger = Logger("UserVM")

    init {
        loadAccessTokens()
    }

    fun loadAccessTokens() {
        tokenStorage.loadTokens()?.let {
            _tokensInfo.value = it
            logger.debug("Tokens cargados: $it")
        } ?: logger.debug("No hay tokens guardados")
    }

    fun loginUser(user: String, password: String) {
        viewModelScope.launch {
            try {
                val tokens = loginRepository.login(user, password)
                _tokensInfo.value = tokens
                _loginError.value = null
            } catch (e: Exception) {
                logger.error(e)
                _loginError.value = "ERROR al iniciar sesión"
            }
        }
    }

    fun logout() {
        tokenStorage.clear()
        _tokensInfo.value = null
    }
}