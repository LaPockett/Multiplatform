package com.dian.prueba.viewModel

import org.junit.Test
import kotlin.test.assertNull

/**
 * POR VER
 */
class LoginVMTest {
    private val viewModel = LoginVM()
    //private val loginRepo = LoginRepo()

    @Test
    fun getRandomString() {
        val result = viewModel.getRandomString()
        assert(result.length == 10)
    }

    @Test
    fun getTokens() {
        assertNull(viewModel.tokens.value)
    }

    @Test
    fun loginUser() {
    }

    @Test
    fun loadSavedTokens() {
    }
}