package com.dian.prueba.viewModel

import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.network.APIClient
import com.dian.prueba.utilities.UpdateStorage
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class UpdateVMTest {
    private lateinit var viewModel: UpdateVM
    private lateinit var mockAPIClient: MockAPIClient

    private val testUpdateInfo = UpdateInfo(
        mustUpdate = true,
        currentVersion = "1.2",
        newVersion = "1.3"
    )

    private class MockAPIClient : APIClient() {
        var forcedResponse: UpdateInfo? = null

        override fun checkUpdateAvailable(): UpdateInfo {
            return forcedResponse ?: UpdateInfo(
                mustUpdate = true,
                currentVersion = "1.2",
                newVersion = "1.3"
            )
        }
    }

    /**
     * Configuración inicial
     * Crea una instancia de UpdateVM con un mock de APIClient
     * Limpia las preferencias de actualización antes de cada prueba
     */
    @Before
    fun setup() {
        mockAPIClient = MockAPIClient()
        viewModel = UpdateVM(mockAPIClient)
        UpdateStorage.settings.clear()
    }

    @Test
    fun getUpdateInfo_returnsExpectedValue() {
        viewModel.updateInfo.value = testUpdateInfo
        assertEquals(testUpdateInfo, viewModel.updateInfo.value)
    }

    @Test
    fun getShowUpdateDialog_isTrue_WhenUpdateIsRequired() {
        viewModel.updateInfo.value = testUpdateInfo
        viewModel.showUpdateDialog.value = testUpdateInfo.updateAvailable && testUpdateInfo.mustUpdate
        assertTrue(viewModel.showUpdateDialog.value)
    }

    @Test
    fun checkForUpdates_retrievesExpectedUpdateInfo() = runTest {
        mockAPIClient.forcedResponse = testUpdateInfo

        viewModel.checkForUpdates()
        val result = viewModel.updateInfo.value

        assertNotNull(result)
        assertTrue(result.updateAvailable)
        assertEquals("1.2", result.currentVersion)
        assertEquals("1.3", result.newVersion)
        assertTrue(viewModel.showUpdateDialog.value)
    }

    @Test
    fun doUpdate_shouldUpdatesToNewVersionAnHideDialog() = runTest {
        UpdateStorage.saveUpdateAvailable(testUpdateInfo)
        viewModel.updateInfo.value = testUpdateInfo
        viewModel.showUpdateDialog.value = true

        viewModel.doUpdate()
        val updatedInfo = viewModel.updateInfo.value

        assertEquals("1.3", updatedInfo?.currentVersion)
        assertEquals("1.3", updatedInfo?.newVersion)
        assertFalse(updatedInfo?.mustUpdate!!)
        assertFalse(viewModel.showUpdateDialog.value)
    }
}