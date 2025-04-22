package com.dian.prueba.API

class APIClient {
    // Getter de propiedad - función que se utiliza para obtener el valor de una propiedad
    // ya sea de una clase u objeto.

    private var _loginToken: String? = null
    val loginToken: String?
        get() {
            return _loginToken
        }

    fun requestLogin(){
        _loginToken = "login_token"
    }

}

data class Post(
    val userId: String,
    val id: String,
    val title: Int,
    val body: Int
)