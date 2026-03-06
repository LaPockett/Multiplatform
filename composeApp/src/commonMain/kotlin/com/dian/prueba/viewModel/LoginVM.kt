package com.dian.prueba.viewModel

import com.dian.prueba.utilities.Logger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import androidx.lifecycle.*
import com.dian.prueba.data.unused.Tokens
import com.dian.prueba.repository.LoginRepository
import com.dian.prueba.utilities.TokenStorage

/* *
 * ViewModel para manejar la lógica de la pantalla de inicio de sesión,
 * y que además se comunica con TokenStorage para cargar y limpiar los tokens.
 */
class LoginVM(
    private val loginRepository:LoginRepository,
    private val tokenStorage: TokenStorage
) : ViewModel() {
    private val logger = Logger("LoginVM")
    private val _tokens = MutableStateFlow<Tokens?>(null)
    val tokens: StateFlow<Tokens?> = _tokens

    fun getRandomString(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..10)
            .map { allowedChars.random() }
            .joinToString("")

    }
    fun loginUser(id: Int) {
        viewModelScope.launch {
            val access = loginRepository.login(id)
            access?.let {
                logger.warn("Haciendo login en LoginViewModel...")

                val t = Tokens(accessToken = it, refreshToken = getRandomString())
                _tokens.value = t
                tokenStorage.saveTokens(t)
                logger.debug(t.toString())
            }
        }
    }

    fun loadSavedTokens() {
        _tokens.value = tokenStorage.loadTokens()
    }
    fun clearSavedTokens() {
        tokenStorage.clear()
    }

}