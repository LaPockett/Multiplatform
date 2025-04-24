package com.dian.prueba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dian.prueba.API.APIClient
import com.dian.prueba.Log.Logger
import com.dian.prueba.HeaderManager.WebViewHeaderManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    /**
     * Inicializo la clase APIClient y Loger
     */
    val apiClient = APIClient()
    private val logger = Logger()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Lanzo la coroutine en el hilo IO para realizar la petición del login
        CoroutineScope(Dispatchers.IO).launch {
            /**
             * Aquí le pasamos el id de usuario que queremos pedir el login
             * Solo hay 10 usuarios en el jsonplaceholder.typicode.com/users
             * así que solo habría que poner números del 0 al 9
             */
            apiClient.requestLogin(5)?.let { token ->
                WebViewHeaderManager.updateLoginCookie(token)
                /**
                 * Dian probando el acceso a la variable loginToken
                 * que es pública e inmutable en la clase APIClient
                 */
                var tokenL = apiClient.loginToken
                logger.warn(tokenL.toString(), "tokenTemporal1")
                // Voy a intentar darle otro valor a loginToken (tokenL)
                tokenL = "Nuevo token"
                logger.warn(tokenL.toString(), "tokenTemporal2")
                // En teoría cambiamos el valor de "loginToken"
                logger.warn(apiClient.loginToken.toString(), "loginToken")
                /**
                 * LOGS
                 * DIAN WARN - Respuesta [tokenTemporal1]: Lucio_Hettinger@annie.ca
                 * DIAN WARN - Respuesta [tokenTemporal2]: Nuevo token
                 * DIAN WARN - Respuesta [loginToken]: Lucio_Hettinger@annie.ca
                 * La variable loginToken sigue siendo Lucio_Hettinger@annie.ca
                 */
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