package com.dian.prueba.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import org.adman.kmp.webview.KmpWebViewScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState


@Composable
fun WebViewCustom (
    modifier: Modifier =  Modifier
) {
    WebView(
        state = rememberWebViewState("https://google.com"),
        modifier = modifier
    )
}