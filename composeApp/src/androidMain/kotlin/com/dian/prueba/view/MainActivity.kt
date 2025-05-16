package com.dian.prueba.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dian.prueba.ui.components.MenuDrawer

class MainActivity : ComponentActivity() {
    //private val loginViewModel = LoginVM()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //loginViewModel.loadSavedTokens()

        /*lifecycleScope.launch {
            loginViewModel.tokens.collectLatest { token ->
                token?.accessToken?.let {
                    WebViewHeaderManager.updateAccessToken(it)
                }
                token?.refreshToken?.let {
                    WebViewHeaderManager.updateAccessToken(it)
                    WebViewHeaderManager.updateRefreshToken(it)
                }
            }
        }*/
        setContent {
            //App()
            //AppNavigation()
            //->AppLogin()
            MenuDrawer()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    //AppNavigation()
    //->AppLogin()
    MenuDrawer()
    //App()
}
