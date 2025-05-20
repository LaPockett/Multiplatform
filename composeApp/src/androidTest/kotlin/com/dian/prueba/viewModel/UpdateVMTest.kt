package com.dian.prueba.viewModel

import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.utilities.UpdateStorage
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.*

class UpdateVMTest {
    private var viewModel: UpdateVM = UpdateVM()
    private val updateInfo: UpdateInfo = UpdateInfo(
        mustUpdate = true,
        currentVersion = "1.2",
        newVersion = "1.3"
    )

    /**
     * Intenté poner el nombre de las funciones con backticks para poder separar las palabras,
     * lo vi en un vídeo de Aris y me pareció que era una buena práctica,
     * pero no funcionó así que se queda en estilo CamelCase de toda la vida.
     */
    @Test
    fun getUpdateInfo_returnsExpectedValue() {
        viewModel.updateInfo.value = updateInfo
        assert(viewModel.updateInfo.value == updateInfo)
    }

    @Test
    fun getShowUpdateDialog_isTrue_WhenUpdateIsRequired() {
        viewModel.updateInfo.value = updateInfo
        viewModel.showUpdateDialog.value = updateInfo.updateAvailable && updateInfo.mustUpdate
        assertTrue(viewModel.showUpdateDialog.value)
    }

    @Test
    fun checkForUpdates_retrievesExpectedUpdateInfo() = runTest {
        UpdateStorage.settings.clear()
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
        viewModel.updateInfo.value = updateInfo
        viewModel.showUpdateDialog.value =true
        viewModel.doUpdate()
        val updatedInfo = viewModel.updateInfo.value

        assertEquals("1.3", updatedInfo?.currentVersion)
        assertEquals("1.3", updatedInfo?.newVersion)
        assertFalse(updatedInfo?.mustUpdate!!)
        assertFalse(viewModel.showUpdateDialog.value)

    }
}