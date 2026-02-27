package com.dian.prueba.viewModel

import com.dian.prueba.data.unused.Tokens
import com.dian.prueba.repository.LoginRepositoryImpl
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.TokenStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
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
// https://stackoverflow.com/questions/58303961/kotlin-coroutine-unit-test-fails-with-module-with-the-main-dispatcher-had-faile
class MainDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
class LoginVMTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    val logger = Logger("LoginVMTest")
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

        if (fakeApiService.requestLogin("1") != null) {
            fakeTokenStorage.saveTokens(
                Tokens(
                    fakeApiService.requestLogin("1")!!,
                    loginViewModel.getRandomString()
                )
            )
        }

        val tokens = fakeTokenStorage.loadTokens()
        logger.debug(tokens.toString())
        assertNotNull(tokens)
        assertEquals("Sincere@april.biz", tokens.accessToken)
        assertNotNull(tokens.refreshToken)
    }

    @Test
    fun `write invalid ID and check the tokens is NULL`() = runTest {
        loginViewModel.loginUser(12)
        if (fakeApiService.requestLogin("12") != null) {
            fakeTokenStorage.saveTokens(
                Tokens(
                    fakeApiService.requestLogin("12")!!,
                    null
                )
            )
        }
        val tokens = fakeTokenStorage.loadTokens()
        logger.debug(tokens.toString())
        assertNull(tokens)
    }

    @Test
    fun `check the request login return not_null if we write a valid ID`() = runTest {
        val result = fakeApiService.requestLogin("1")
        logger.debug(result.toString())
        assertNotNull(result)
    }

    @Test
    fun `check the request login return null if we write a invalid ID`() = runTest {
        val result = fakeApiService.requestLogin("12")
        logger.debug(result.toString())
        assert(result == null)
    }
}