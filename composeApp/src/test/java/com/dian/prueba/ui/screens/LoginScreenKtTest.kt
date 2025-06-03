package com.dian.prueba.ui.screens

import com.dian.prueba.utilities.LoginValidator.validateLogin
import com.dian.prueba.utilities.Resultado
import org.junit.Test
import kotlin.test.assertTrue

class LoginScreenKtTest {

    @Test
    fun `validate login when email and password are valid`() {
        val validEmail = "dian@example.com"
        val validPassword = "password123"
        val result = validateLogin(validEmail, validPassword)
        assertTrue(result == Resultado.Valid)
    }

    @Test
    fun `validate login when email and password are not valid`() {
        val validEmail = "dian@example.com"
        val invalidPassword = "123"
        val result = validateLogin(validEmail, invalidPassword)
        assertTrue(result == Resultado.Invalid)
    }

    @Test
    fun `validate login when email and password are empty`() {
        val invalidEmail = ""
        val invalidPassword = ""
        val result = validateLogin(invalidEmail, invalidPassword)
        assertTrue(result == Resultado.Empty)
    }

    @Test
    fun `validate login when email or password is empty`() {
        val validEmail = "dian@example.com"
        val invalidPassword = ""
        val result = validateLogin(validEmail, invalidPassword)
        assertTrue(result == Resultado.Empty)
    }

}