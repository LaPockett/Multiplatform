package com.dian.prueba.viewModel

import com.dian.prueba.repository.LoginRepo
import com.dian.prueba.utilities.Logger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import androidx.lifecycle.*
import com.dian.prueba.model.Tokens
import com.dian.prueba.utilities.TokenStorage

class LoginVM(
    private val loginRepo: LoginRepo = LoginRepo()
) : ViewModel() {

    private val logger = Logger()
    private val _tokens = MutableStateFlow<Tokens?>(null)
    val tokens: StateFlow<Tokens?> = _tokens

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

}