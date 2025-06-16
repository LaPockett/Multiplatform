package com.dian.prueba
import android.annotation.SuppressLint
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


/*class UpdateAlertDialogKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @SuppressLint("ViewModelConstructorInComposable")
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
}*/