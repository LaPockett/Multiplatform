package com.dian.prueba

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.runComposeUiTest
import com.dian.prueba.pruebaUITest.CounterScreen
import com.dian.prueba.pruebaUITest.CounterViewModel
import kotlin.test.Test

/**
 * PRUEBA UI TEST
 */
class CounterScreenTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testButtonIncrementsCounter() = runComposeUiTest {
        val viewModel = CounterViewModel()

        setContent {
            CounterScreen(viewModel = viewModel)
        }

        onNodeWithTag("counterText").assertTextEquals("0")
        onNodeWithTag("incrementButton").performClick()
        onNodeWithTag("counterText").assertTextEquals("1")
    }
}