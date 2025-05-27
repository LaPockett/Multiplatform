package common.example.search

import com.dian.prueba.logger
import com.dian.prueba.model.Login
import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.network.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * FakeApiService para uso de LoginVMTest y UpdateVMTest
 */
class FakeApiService : ApiService {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    override fun checkUpdateAvailable(): UpdateInfo {
        return UpdateInfo(
            mustUpdate = true,
            currentVersion = "1.2",
            newVersion = "1.3"
        )
    }

    override suspend fun requestLogin(id: String): String? {
        if (id.toInt() in 1..10) {
            val result: Login = client.get("https://jsonplaceholder.typicode.com/users/$id").body()
            logger.debug(result.toString(), "JSON Response")
            return result.email
        } else {
            return null
        }
    }
}