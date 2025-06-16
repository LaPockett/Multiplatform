package com.dian.prueba

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import com.dian.prueba.ui.components.WebViewAccount
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WebViewAccountKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                WebViewAccount()
            }
        }
    }

    @Test
    fun testWebViewAccount() {
        composeTestRule.onNodeWithTag("WebViewAccount").assertExists()
    }

}