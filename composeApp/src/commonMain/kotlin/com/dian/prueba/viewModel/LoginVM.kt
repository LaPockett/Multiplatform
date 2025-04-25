package com.dian.prueba.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dian.prueba.repository.LoginRepo
import com.dian.prueba.utilities.Logger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*

/**
 * Clase para manejar el login
 */
class LoginVM (
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
}