package common.example.search

import com.dian.prueba.logger
import com.dian.prueba.model.Login
import com.dian.prueba.model.Tokens
import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.network.ApiService
import com.dian.prueba.repository.LoginRepositoryImpl
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.TokenStorage
import com.dian.prueba.viewModel.LoginVM
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.*

class FakeTokenStorage : TokenStorage {
    private var tokens: Tokens? = null

    override fun saveTokens(tokens: Tokens) {
        this.tokens = tokens
    }

    override fun loadTokens(): Tokens? {
        return tokens
    }

    override fun clear() {
        tokens = null
    }
}

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
        TODO("ESTO NO SE VA A USAR EN ESTE CASO")
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
class LoginVMTest {

    private lateinit var fakeApiService: FakeApiService
    private lateinit var fakeTokenStorage: FakeTokenStorage
    private lateinit var loginViewModel: LoginVM

    @Before
    fun setUp() {
        fakeApiService = FakeApiService()
        fakeTokenStorage = FakeTokenStorage()
        val loginRepository = LoginRepositoryImpl(fakeApiService)
        loginViewModel = LoginVM(loginRepository, fakeTokenStorage)
    }
    @After
    fun tearDown() {
        fakeTokenStorage.clear()
    }

    @Test
    fun `write valid ID and check the tokens is NOT NULL`() = runTest {
        loginViewModel.loginUser(1)
        /*fakeApiService.requestLogin("1")?.let {
            Tokens(it, loginViewModel.getRandomString())
            logger.debug(Tokens(it, loginViewModel.getRandomString()).toString(), "LoginVMTest - tokens")
        }*/
        if (fakeApiService.requestLogin("1") != null) {
            fakeTokenStorage.saveTokens(
                Tokens(
                    fakeApiService.requestLogin("1")!!,
                    loginViewModel.getRandomString()
                )
            )
        }

        val tokens = fakeTokenStorage.loadTokens()
        logger.debug(
            tokens.toString(),
            "LoginVMTest - write valid ID and check the TOKENS is not null"
        )
        assertNotNull(tokens)
    }
    @Test
    fun `write invalid ID and check the tokens is NULL`() = runTest {
        loginViewModel.loginUser(12)
        if (fakeApiService.requestLogin("12")!=null) {
            fakeTokenStorage.saveTokens(
                Tokens(
                    fakeApiService.requestLogin("12")!!,
                    null
                )
            )
        }
        val tokens = fakeTokenStorage.loadTokens()
        logger.debug(
            tokens.toString(),
            "LoginVMTest - write invalid ID and check the tokens is NULL"
        )
        assertNull(tokens)
    }

    @Test
    fun `check the request login return not_null if we write a valid ID`() = runTest {
        val result = fakeApiService.requestLogin("1")
        logger.debug(
            result.toString(),
            "LoginVMTest - write valid ID and check the result is not null"
        )
        assertNotNull(result)
    }

    @Test
    fun `check the request login return null if we write a invalid ID`() = runTest {
        val result = fakeApiService.requestLogin("12")
        logger.debug(
            result.toString(),
            "LoginVMTest - check the request login return null if we write a invalid ID"
        )
        assert(result == null)
    }


    /*@Test
fun `prueba return false` ()= runTest {
    fakeApiService.firstLogin = false
    val checkLogin = fakeApiService.checkLogin()
    logger.debug(fakeApiService.firstLogin.toString(), "LoginVMTest - prueba1")
    logger.debug(checkLogin.toString(), "LoginVMTest - prueba1")
}
@Test
fun `prueba return true` ()= runTest {
    fakeApiService.firstLogin = true
    val checkLogin = fakeApiService.checkLogin()
    logger.debug(fakeApiService.firstLogin.toString(), "LoginVMTest - prueba2")
    logger.debug(checkLogin.toString(), "LoginVMTest - prueba2")
}*/

}