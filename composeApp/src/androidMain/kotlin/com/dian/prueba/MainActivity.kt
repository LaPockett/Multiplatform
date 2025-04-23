package com.dian.prueba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dian.prueba.API.APIClient
import com.dian.prueba.HeaderManager.WebViewHeaderManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    // Inicializo la clase APIClient
    val apiClient = APIClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Lanzo la coroutine en el hilo IO para realizar la petición del login
        CoroutineScope(Dispatchers.IO).launch {
            // Aquí le pasamos el id de usuario que queremos pedir el login
            // Solo hay 10 usuarios en el jsonplaceholder.typicode.com/users
            // así que solo habría que poner números del 0 al 9
            apiClient.requestLogin(5)?.let { token ->
                WebViewHeaderManager.updateLoginCookie(token)
                println("DIAN LOG - Token guardado en headers: $token")
            }
        }
        setContent {
            AppNavigation()
            //App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    AppNavigation()
    //App()
}