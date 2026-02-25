package com.dian.prueba.viewModel

import com.dian.prueba.data.model.UpdateInfo
import com.dian.prueba.network.ApiService

/**
 * FakeApiService para uso de LoginVMTest y UpdateVMTest
 * Anotaciones:
 * - En mocking nunca se llaman a APIs ni recursos externos, lo que se hace
 *   simular una respuesta falsa
 */
class FakeApiService : ApiService {

    // Para UpdateVMTest
    override fun checkUpdateAvailable(): UpdateInfo {
        return UpdateInfo(
            mustUpdate = true,
            currentVersion = "1.2",
            newVersion = "1.3"
        )
    }

    // Para LoginVMTest
    override suspend fun requestLogin(id: String): String? {
        return if (id.toInt() in 1..10) {
            "Sincere@april.biz"
        } else {
            null
        }
    }
}