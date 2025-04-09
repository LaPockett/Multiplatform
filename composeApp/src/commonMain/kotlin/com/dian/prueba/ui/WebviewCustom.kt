package com.dian.prueba.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState


@Composable
fun WebViewCustom (
    modifier: Modifier =  Modifier
) {
    WebView(
        state = rememberWebViewState("https://www.amazon.es/"),
        modifier = modifier
    )
}