package com.dian.prueba

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.runComposeUiTest
import com.dian.prueba.pruebaUITest.CounterScreen
import com.dian.prueba.pruebaUITest.CounterViewModel
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * PRUEBA UI TEST
 * - funciona
 */
/*class CounterScreenTest {

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
}*/

/**
 * Esto sí funciona tanto con semantics como sin semantics en el Composable
 */
class CounterScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                CounterScreen(viewModel = CounterViewModel())
            }
        }
    }

    @Test
    fun testButtonIncrementsCounter()  {
        composeTestRule.onNodeWithTag("counterText").assertTextEquals("0")
        composeTestRule.onNodeWithTag("incrementButton").performClick()
        composeTestRule.onNodeWithTag("counterText").assertTextEquals("1")
    }
}