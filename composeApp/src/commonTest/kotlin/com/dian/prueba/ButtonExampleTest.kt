package com.dian.prueba

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.dian.prueba.pruebaUITest.ButtonAdd
import kotlinx.coroutines.delay
import kotlin.test.Test

/**
 * PRUEBA UI TEST
 * - funciona
 */
class ButtonExampleTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testButton() = runComposeUiTest {
        setContent {
            ButtonAdd(
                Color.Red
            )
        }
        onNodeWithTag("ButtonAdd").assertExists()
        waitForIdle()
        onNodeWithTag("TextAdd", useUnmergedTree = true).assertExists()
        waitForIdle()
        onNodeWithTag("AddIcon", useUnmergedTree = true).assertExists()
        waitForIdle()
    }

}