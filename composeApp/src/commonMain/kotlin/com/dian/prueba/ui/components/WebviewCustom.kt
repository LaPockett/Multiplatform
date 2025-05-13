package com.dian.prueba.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.*
import com.dian.prueba.utilities.Logger
import com.dian.prueba.repository.WebViewHeaderManager
import com.multiplatform.webview.web.WebContent
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState



@Composable
fun WebViewHome (
    query: String,
    modifier: Modifier =  Modifier
) {
    val logger = Logger("WebViewHome")
    val url = if (query.isNotEmpty()) {
        "https://www.amazon.es/s?k=$query"
    } else { "https://www.amazon.es/" }

    val headers = WebViewHeaderManager.getHeaders()
    logger.debug(headers.toString())

    val webViewState = rememberWebViewState(
        url = url,
        additionalHttpHeaders = headers
    )
    LaunchedEffect(url){
        webViewState.content= WebContent.Url(url, additionalHttpHeaders = headers)
    }
    WebView(
        state = webViewState,
        modifier = modifier.fillMaxSize().padding(top = 10.dp)
    )
}

@Composable
fun WebViewAccount (
    modifier: Modifier =  Modifier,
) {
    val logger = Logger("WebViewAccount")
    val headers = WebViewHeaderManager.getHeaders()
    logger.debug(headers.toString())
    val url = "https://www.amazon.es/ap/register?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.es%2F%3F%26tag%3Dhydesnav-21%26ref%3Dnav_ya_signin%26adgrpid%3D152290669839%26hvpone%3D%26hvptwo%3D%26hvadid%3D672291362554%26hvpos%3D%26hvnetw%3Dg%26hvrand%3D14081245408687620115%26hvqmt%3De%26hvdev%3Dc%26hvdvcmdl%3D%26hvlocint%3D%26hvlocphy%3D9198415%26hvtargid%3Dkwd-10573980%26hydadcr%3D4855_2227860&prevRID=XVDN09KEKKSR3JMSPCVD&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=esflex&openid.mode=checkid_setup&prepopulatedLoginId=&failedSignInCount=0&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&pageId=esflex&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0"

    val webViewState = rememberWebViewState(
        url = url,
        additionalHttpHeaders = headers
    )
    LaunchedEffect(url){
        webViewState.content= WebContent.Url(url, additionalHttpHeaders = headers)
    }

    WebView(
        state = webViewState,
        modifier = Modifier.testTag("WebViewAccount")
    )
}

@Composable
fun WebViewCart (
    modifier: Modifier =  Modifier
) {
    val logger = Logger("WebViewCart")
    val headers = WebViewHeaderManager.getHeaders()
    logger.debug(headers.toString())

    val url = "https://www.amazon.es/gp/cart/view.html?ref_=nav_cart"
    val webViewState = rememberWebViewState(
        url = url,
        additionalHttpHeaders = headers
    )
    LaunchedEffect(url){
        webViewState.content= WebContent.Url(url, additionalHttpHeaders = headers)
    }

    WebView(
        state = webViewState
    )
}
@Composable
fun WebViewSettings(
    modifier: Modifier =  Modifier
){
    val url = "https://www.amazon.es/gp/help/customer/display.html?nodeId=G3JHAKLM2QKM6NXS"
    val webViewState = rememberWebViewState(
        url = url
    )
    LaunchedEffect(url){
        webViewState.content= WebContent.Url(url)
    }
    WebView(
        state = webViewState
    )

}

@Composable
fun WebViewSearch (
    modifier: Modifier =  Modifier
) {
    val logger = Logger("WebViewSearch")
    val headers = WebViewHeaderManager.getHeaders()
    logger.debug(headers.toString())
    val url = "https://www.amazon.es/gp/bestsellers/?ref_=nav_cs_bestsellers"

    val webViewState = rememberWebViewState(
        url = url,
        additionalHttpHeaders = headers
    )
    LaunchedEffect(url){
        webViewState.content= WebContent.Url(url, additionalHttpHeaders = headers)
    }

    WebView(
        state = webViewState,
        modifier = modifier.fillMaxSize()
    )
}