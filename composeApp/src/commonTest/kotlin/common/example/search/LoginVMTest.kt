package common.example.search

import com.dian.prueba.network.APIClient
import com.dian.prueba.repository.LoginRepositoryImpl
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.TokenStorage
import com.dian.prueba.viewModel.LoginVM
import com.russhwolf.settings.MapSettings
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class LoginVMTest {
    private val apiClient = APIClient()
    private val loginViewModel = LoginVM(
        loginRepository = LoginRepositoryImpl()
    )
    private val logger = Logger()

    @Before
    fun setUp() {
        //TokenStorage.init(MapSettings())
        logger.debug(loginViewModel.tokens.value.toString(), "LoginVMTest - setup")
        TokenStorage.settings.clear()
    }

    @Test
    fun firstLogin_nullTokens(){
        logger.debug(loginViewModel.tokens.value.toString(), "LoginVMTest - firstLogin_nullTokens")
        assert(loginViewModel.tokens.value == null)
    }
    @Test
    fun afterFirstLogin_notNullTokens(){
        loginViewModel.loginUser(2)
        logger.debug(loginViewModel.tokens.value.toString(), "LoginVMTest - afterFirstLogin_notNullTokens")
        assertNotNull(loginViewModel.tokens.value != null)
    }

    @Test
    fun checkTheRequestLogin_returnNotNull() = runTest{
        val result = apiClient.requestLogin("3")
        logger.debug(result.toString(), "LoginVMTest - checkTheRequestLogin_returnNotNull")
        assertNotNull(result)
    }

    @Test
    fun checkTheRequestLogin_returnNull() = runTest{
        val result = apiClient.requestLogin("0")
        logger.debug(result.toString(), "LoginVMTest - checkTheRequestLogin_returnNull")
        assertNull(result)
    }

    @Test
    fun checkTokens_returnNotNull() = runTest{
        //loginViewModel.loginUser(5)
        val tokens = TokenStorage.loadTokens()
        logger.debug(TokenStorage.loadTokens().toString(), "LoginVMTest - checkTokens_returnNotNull")
        assertNotNull(tokens)

    }

}