package com.dian.prueba.utilities

object LoginValidator {
    fun validateLogin(email: String, password: String): Resultado {
        if (email.isEmpty() || password.isEmpty()) {
            return Resultado.Empty
        }
        if (!isEmailValid(email) || !isPasswordValid(password)) {
            return Resultado.Invalid
        }
        return Resultado.Valid
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length in 6..20
    }
}

sealed class Resultado {
    object Valid : Resultado()
    object Empty : Resultado()
    object Invalid : Resultado()
}