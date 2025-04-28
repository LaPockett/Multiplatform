package com.dian.prueba.viewModel

import com.dian.prueba.repository.LoginRepo
import com.dian.prueba.utilities.Logger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import androidx.lifecycle.*

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

class LoginVModel(
    private val loginRepo: LoginRepo = LoginRepo()
): ViewModel() {
    private val logger = Logger()
    private val _loginToken = MutableStateFlow<String?>("")
    val loginToken : StateFlow<String?> = _loginToken

    fun loginUser(token: String){
        viewModelScope.launch {
            logger.warn("Haciendo login...", "LoginViewModel")
            val loginToken = loginRepo.login(token)
            _loginToken.value = loginToken
        }

    }
}