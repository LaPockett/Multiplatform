package com.dian.prueba.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.dian.prueba.App
import com.dian.prueba.AppLogin
import com.dian.prueba.AppNavigation
import com.dian.prueba.HeaderManager.WebViewHeaderManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    //private val loginViewModel = LoginVM()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //loginViewModel.loginUser(5)
        /*lifecycleScope.launch {
            loginViewModel.loginToken.collectLatest {
                    token -> token?.let {
                WebViewHeaderManager.updateLoginCookie(it)
            }
            }
        }*/
        setContent {
            //App()
            //AppNavigation()
            AppLogin()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    //AppNavigation()
    AppLogin()
    //App()
}
