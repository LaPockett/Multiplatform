package com.dian.prueba.network

import com.dian.prueba.model.Login
import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.UpdateStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

interface ApiService {
    fun checkUpdateAvailable(): UpdateInfo
    suspend fun requestLogin(id: String): String?
}

class APIClient (
    private val updateStorage: UpdateStorage
) : ApiService {
    private val logger = Logger("APIClient")
    private var _loginToken: String? = null

    // Essential line in case you want to use expect/actual for Ktor
    //private val client = createHttpClient()

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    override suspend fun requestLogin(id: String): String? {
        logger.warn("Iniciando login...")

        return try {
            withContext(Dispatchers.Default) {
                val result: Login = client.get("https://jsonplaceholder.typicode.com/users/$id").body()
                logger.debug(result.toString())
                _loginToken = result.email
                logger.debug(_loginToken.toString())
                _loginToken
            }
        } catch (e: Exception) {
            logger.error(e)
            null
        }
    }

    override fun checkUpdateAvailable(): UpdateInfo {
        logger.warn("Checking for updates...")
        updateStorage.loadUpdateInfo()?.let { savedInfo ->
            if (savedInfo.currentVersion == savedInfo.newVersion){
                return savedInfo
            }
        }
        return UpdateInfo(
            mustUpdate = true,
            currentVersion = "1.2",
            newVersion = "1.3", // Desde aquí podemos cambiar a otra versión
        )
    }
}

// Prueba del servidor en Ktor
@Serializable
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

interface ApiServiceTest {
    suspend fun getPosts(): List<Post>
}

class APIClientTest : ApiServiceTest {
    private val logger = Logger("APIClient")
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    override suspend fun getPosts(): List<Post> = withContext(Dispatchers.Default) {
       try {
            logger.warn("Solicitando posts al servidor Ktor...")
            val baseUrl = "http://127.0.0.1:8080/posts"

            val result: List<Post> = client.get(baseUrl).body()
            logger.debug("Recibidos ${result.size} posts")
            result
        } catch (e: Exception) {
            logger.error(e)
            emptyList()
        }
    }
}

fun main() = runBlocking {
    val api = APIClientTest()
    val posts = api.getPosts()
    println("Resultado de los posts: ")
    posts.take(5).forEach { println(it) }
}
