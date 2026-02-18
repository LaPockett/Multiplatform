package com.dian.prueba.utilities

import com.dian.prueba.data.model.Tokens
import com.russhwolf.settings.set
import com.russhwolf.settings.Settings

interface TokenStorage {
    fun saveTokens(tokens: Tokens)
    fun loadTokens(): Tokens?
    fun clear()
}
class TokenStorageImpl(private val settings: Settings) : TokenStorage {
    private companion object {
        const val ACCESS_TOKEN_KEY = "access_token"
        const val REFRESH_TOKEN_KEY = "refresh_token"
    }

    override fun saveTokens(tokens: Tokens) {
        settings[ACCESS_TOKEN_KEY] = tokens.accessToken
        tokens.refreshToken?.let {
            settings[REFRESH_TOKEN_KEY] = it
        }
    }

    override fun loadTokens(): Tokens? {
        val access = settings.getStringOrNull(ACCESS_TOKEN_KEY) ?: return null
        val refresh = settings.getStringOrNull(REFRESH_TOKEN_KEY)
        return Tokens(access, refresh)
    }

    override fun clear() {
        settings.remove(ACCESS_TOKEN_KEY)
        settings.remove(REFRESH_TOKEN_KEY)
    }
}