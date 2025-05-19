package com.dian.prueba.viewModel

import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.network.APIClient
import org.junit.Test

class UpdateVMTest {
    private var viewModel: UpdateVM = UpdateVM()
    //private var apiClient: APIClient = APIClient()
    private var updateInfo: UpdateInfo = UpdateInfo(
        mustUpdate = true,
        currentVersion = "1.2",
        newVersion = "1.3"
    )
    //private var showUpdateDialog: Boolean = true

    /*@Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }*/

    @Test
    fun getUpdateInfo() {
        viewModel.updateInfo.value = updateInfo
        assert(viewModel.updateInfo.value == updateInfo)
    }

    @Test
    fun getShowUpdateDialog() {
    }

    @Test
    fun checkForUpdates() {
    }

    @Test
    fun doUpdate() {
    }
}