package com.dian.prueba.utilities

import com.dian.prueba.model.Tokens
import com.russhwolf.settings.set
import com.russhwolf.settings.Settings

object TokenStorage {
    private const val ACCESS_TOKEN_KEY = "access_token"
    private const val REFRESH_TOKEN_KEY = "refresh_token"

    private val settings = Settings()

    fun saveTokens(tokens: Tokens) {
        settings[ACCESS_TOKEN_KEY] = tokens.accessToken
        tokens.refreshToken?.let {
            settings[REFRESH_TOKEN_KEY] = it
        }
    }

    fun loadTokens(): Tokens? {
        val access = settings.getStringOrNull(ACCESS_TOKEN_KEY) ?: return null
        val refresh = settings.getStringOrNull(REFRESH_TOKEN_KEY)
        return Tokens(access, refresh)
    }

}