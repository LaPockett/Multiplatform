package com.dian.prueba
import androidx.compose.material3.MaterialTheme
import com.dian.prueba.network.APIClient
import com.dian.prueba.utilities.UpdateStorageImpl
import com.dian.prueba.viewModel.UpdateVM
import com.russhwolf.settings.Settings
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.dian.prueba.ui.components.dialogs.UpdateAlertDialog
import kotlinx.coroutines.awaitAll
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Este es el test que no funciona, los demás sí funcionan
 */

/*class UpdateAlertDialogKtTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testUpdateAlertDialog() = runComposeUiTest {
        val updateVM = UpdateVM(
            updateStorage = UpdateStorageImpl(
                settings = Settings()
            ),
            apiService = APIClient(
                updateStorage = UpdateStorageImpl(
                    settings = Settings()
                )
            )
        )
        setContent {
            UpdateAlertDialog(
                viewModel = updateVM
            )
        }
        onNodeWithTag("updateTitle").assertExists()
        onNodeWithTag("updateButton").performClick()
        // El usuario tiene que ser redirigido a https://play.google.com/store/apps/details?id=com.amazon.mShop.android.shopping&hl=es&pli=1

    }
}*/
class UpdateAlertDialogKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                UpdateAlertDialog(
                    viewModel = UpdateVM(
                        updateStorage = UpdateStorageImpl(
                            settings = Settings()
                        ),
                        apiService = APIClient(
                            updateStorage = UpdateStorageImpl(
                                settings = Settings()
                            )
                        )
                    ))
            }
        }
    }

    @Test
    fun testUpdateAlertDialog()  {
        composeTestRule.onNodeWithTag("updateTitle").assertExists()
        composeTestRule.onNodeWithTag("updateButton", useUnmergedTree = true).performClick()
    }
}