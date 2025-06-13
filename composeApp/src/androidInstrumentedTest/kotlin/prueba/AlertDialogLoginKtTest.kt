package com.dian.prueba

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import com.dian.prueba.ui.components.dialogs.showAlertDialogLogin
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Esto
 */
class AlertDialogLoginKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                showAlertDialogLogin("Esto es un título", onDismissRequest = {})
            }
        }
    }
    @Test
    fun testAlertDialogLogin() {
        composeTestRule.onNodeWithTag("AlertDialog").assertExists()
        composeTestRule.onNodeWithTag("loginDialogButton").assertExists()
        composeTestRule.onNodeWithTag("loginDialogButton").performClick()
        composeTestRule.onNodeWithTag("AlertDialog").assertHasNoClickAction()
        composeTestRule.onNodeWithTag("AlertDialog").printToLog("PRUEBA")
    }

}