package com.dian.prueba

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import com.dian.prueba.ui.components.TopAppBarScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Este test sí que funciona
 * Lo que me hace pensar que testTAg no funciona con dialogos
 */
class TopAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                TopAppBarScreen()
            }
        }
    }
    @Test
    fun testTopAppBarTitle() {
        composeTestRule.onRoot().printToLog("Esto es un mensaje de prueba")
        composeTestRule.onNodeWithTag("TopAppBar").assertExists()
    }
}