package com.dian.prueba.viewModel

import com.dian.prueba.data.model.UpdateInfo
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.UpdateStorage
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FakeUpdateStorage : UpdateStorage {
    private var updateInfo: UpdateInfo? = null

    override fun saveUpdateAvailable(updateInfo: UpdateInfo) {
        this.updateInfo = updateInfo
    }

    override fun loadUpdateInfo(): UpdateInfo? {
        return updateInfo
    }

    override fun updateToNewVersion(newVersion: String): UpdateInfo {
        val updatedInfo = UpdateInfo(
            mustUpdate = false,
            currentVersion = newVersion,
            newVersion = newVersion
        )
        saveUpdateAvailable(updatedInfo)
        return updatedInfo
    }

    override fun clear() {
        updateInfo = null
    }
}

class UpdateVMTest {
    private lateinit var updateViewModel: UpdateVM
    private lateinit var fakeUpdateStorage: FakeUpdateStorage
    private lateinit var fakeApiService: FakeApiService
    private val logger = Logger("UpdateVMTest")

    @Before
    fun setup() {
        fakeUpdateStorage = FakeUpdateStorage()
        fakeApiService = FakeApiService()
        updateViewModel = UpdateVM(fakeUpdateStorage, fakeApiService)
        updateViewModel.checkForUpdates()
    }

    @After
    fun tearDown() {
        fakeUpdateStorage.clear()
    }

    @Test
    fun `get expected values in fakeApiService when the app isn't updated`() = runTest {
        fakeApiService.checkUpdateAvailable()
        val result = updateViewModel.updateInfo.value
        logger.debug("getExpectedValuesInFakeApiService $result")
        assertEquals("1.3", result?.newVersion ?: "none")
        assertEquals("1.2", result?.currentVersion ?: "none")
        assertTrue(result?.mustUpdate ?: true)
    }

    @Test
    fun `get updateInfo and returns expected value if fakeUpdateStorage is not null, updateInfo of VM is not null too`() {
        val updateInfoStorage = fakeUpdateStorage.loadUpdateInfo()
        val updateInfoVM = updateViewModel.updateInfo.value
        logger.debug("updateInfoStorage: $updateInfoStorage")
        logger.debug("updateInfoVM: $updateInfoVM")
        assertEquals(updateInfoStorage, updateInfoVM)
    }

    @Test
    fun `get ShowUpdateDialog is TRUE when update is required`() {
        logger.debug("ShowUpdateDialog true: ${updateViewModel.showUpdateDialog.value}")
        assertTrue(updateViewModel.showUpdateDialog.value)
    }

    @Test
    fun `check for updates with viewModel and retrieves expected UpdateInfo`() = runTest {
        val result = updateViewModel.updateInfo.value
        logger.debug("Check for updates with viewModel: $result")
        assertNotNull(result)
        assertTrue(result.updateAvailable)
        assertEquals("1.2", result.currentVersion)
        assertEquals("1.3", result.newVersion)
        assertTrue(updateViewModel.showUpdateDialog.value)
    }

    @Test
    fun `check for updates with UpdateStorage and retrieves expected UpdateInfo`() = runTest {
        val result = fakeUpdateStorage.loadUpdateInfo()

        assertNotNull(result)
        assertTrue(result.updateAvailable)
        assertEquals("1.2", result.currentVersion)
        assertEquals("1.3", result.newVersion)
        assertTrue(updateViewModel.showUpdateDialog.value)
    }

    @Test
    fun `do Update and should updates to new version 1'3 and showUpdateDialog should be FALSE`() =
        runTest {
            updateViewModel.showUpdateDialog.value = true
            logger.debug("ShowUpdateDialog true: ${updateViewModel.showUpdateDialog.value}")
            updateViewModel.doUpdate()
            val updateInfo = fakeUpdateStorage.loadUpdateInfo()

            assertEquals("1.3", updateInfo!!.currentVersion)
            assertEquals("1.3", updateInfo.newVersion)
            assertFalse(updateInfo.mustUpdate)
            assertFalse(updateViewModel.showUpdateDialog.value)
        }
}