package common.example.search

import com.dian.prueba.viewModel.UpdateVM
import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.network.ApiService
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

class FakeAPIService : ApiService {
    override fun checkUpdateAvailable(): UpdateInfo {
        return UpdateInfo(
            mustUpdate = true,
            currentVersion = "1.2",
            newVersion = "1.3"
        )
    }

    override suspend fun requestLogin(id: String): String? {
        TODO("ESTO NO SE VA A USAR EN ESTE CASO")
    }
}

class UpdateVMTest {
    private lateinit var updateViewModel: UpdateVM
    private lateinit var fakeUpdateStorage: FakeUpdateStorage
    private lateinit var fakeApiService: FakeAPIService
    private val logger = Logger()

    @Before
    fun setup() {
        fakeUpdateStorage = FakeUpdateStorage()
        fakeApiService = FakeAPIService()
        updateViewModel = UpdateVM(fakeUpdateStorage, fakeApiService)
        updateViewModel.checkForUpdates()
    }
    @After
    fun tearDown() {
        fakeUpdateStorage.clear()
    }

    @Test
    fun `get expected values in fakeApiService when the app isn't updated` () = runTest {
        val result = fakeApiService.checkUpdateAvailable()
        logger.debug(result.toString(), "getExpectedValuesInFakeApiService - UpdateVMTest")
        assertEquals("1.3", result.newVersion)
        assertEquals("1.2", result.currentVersion)
        assertTrue(result.mustUpdate)
    }
    @Test
    fun `get updateInfo and returns expected value if fakeUpdateStorage is not null, updateInfo of VM is not null too`() {
        val updateInfoStorage = fakeUpdateStorage.loadUpdateInfo()
        val updateInfoVM = updateViewModel.updateInfo.value
        logger.debug(updateInfoStorage.toString(), "UpdateInfo del Storage - UpdateVMTest")
        logger.debug(updateInfoVM.toString(), "UpdateInfo del VM - UpdateVMTest")
        assertEquals(updateInfoStorage, updateInfoVM)
    }

    @Test
    fun `get ShowUpdateDialog is TRUE when update is required`() {
        logger.debug(updateViewModel.showUpdateDialog.value.toString(), "getShowUpdateDialog_isTrue_WhenUpdateIsRequired - UpdateVMTest")
        assertTrue(updateViewModel.showUpdateDialog.value)
    }

    @Test
    fun `check for updates with viewModel and retrieves expected UpdateInfo`() = runTest {
        val result = updateViewModel.updateInfo.value
        logger.debug(result.toString(), "checkForUpdates_with viewmodel - UpdateVMTest")
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
    fun `do Update and should updates to new version 1'3 and showUpdateDialog should be FALSE`() = runTest {
        updateViewModel.showUpdateDialog.value = true
        logger.debug(updateViewModel.showUpdateDialog.value.toString(), "doUpdate_shouldUpdatesToNewVersionAnHideDialog - UpdateVMTest")
        updateViewModel.doUpdate()
        val updateInfo = fakeUpdateStorage.loadUpdateInfo()

        assertEquals("1.3", updateInfo!!.currentVersion)
        assertEquals("1.3", updateInfo.newVersion)
        assertFalse(updateInfo.mustUpdate)
        assertFalse(updateViewModel.showUpdateDialog.value)
    }

}