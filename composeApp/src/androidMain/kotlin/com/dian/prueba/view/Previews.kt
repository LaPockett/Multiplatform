package com.dian.prueba.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.dian.prueba.AppLogin
import com.dian.prueba.Theme.MultiplatformTheme
import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.navigation.BottomNavigationBar
import com.dian.prueba.network.ApiService
import com.dian.prueba.ui.EmailTextField
import com.dian.prueba.ui.PasswordTextField
import com.dian.prueba.ui.components.TopAppBarMenuDrawer
import com.dian.prueba.ui.components.buttons.CustomButtonWithIcon
import com.dian.prueba.ui.components.buttons.CustomDialogButton
import com.dian.prueba.ui.components.dialogs.InvalidDataAlertDialogLogin
import com.dian.prueba.ui.components.dialogs.ShowAlertDialogLogin
import com.dian.prueba.ui.components.dialogs.UpdateAlertDialog
import com.dian.prueba.ui.screens.LoginScreen
import com.dian.prueba.ui.screens.ScrollRowAmazon
import com.dian.prueba.ui.screens.SearchBarAmazon
import com.dian.prueba.utilities.UpdateStorage
import com.dian.prueba.viewModel.UpdateVM
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

/**
 * Usa este componente para hacer querys de productos en Amazon
 * Example usage:
 * @Composable
 * fun MySearchBarComposable() {
 *     MultiplatformTheme {
 *         SearchBarAmazon(
 *             query = "Mesa",
 *             onQueryChange = { newQuery -> },
 *             onSearch = { query -> },
 *             active = true,
 *             onActiveChange = { active -> }
 *         )
 *     }
 * }
 */
@OptIn(ExperimentalMaterial3Api::class)
@ShowkaseComposable(
    name = "SearchBarAmazon", group = "Amazon components",
    styleName = "Default Style", heightDp = 80
)
@Preview(
    showBackground = true, name = "SearchBarAmazon",
    heightDp = 80
)
@Composable
fun AmazonSearchBarPreview() {
    MultiplatformTheme {
        SearchBarAmazon(
            query = "Mesa",
            onQueryChange = { newQuery -> },
            onSearch = { query -> },
            active = true,
            onActiveChange = { active -> }
        )
    }
}

@ShowkaseComposable(
    name = "ScrollRowAmazon", group = "Amazon components",
    styleName = "Default Style"
)
@Preview(
    showBackground = true,
    name = "ScrollRowAmazon"
)
@Composable
fun AmazonScrollRowPreview() {
    MultiplatformTheme {
        ScrollRowAmazon()
    }
}

@ShowkaseComposable(name = "AppLogin", group = "Amazon components")
@Preview(
    showBackground = true,
    name = "AppLogin"
)
@Composable
fun AmazonLoginPreview() {
    val navController = rememberNavController()
    MultiplatformTheme {
        LoginScreen(navController)
    }
}

/**
 * Usa este componente para navegar por la aplicación mediante un bottom navigation bar
 */
@ShowkaseComposable(
    name = "BottomNavigation", group = "Navigation components",
    styleName = "Default Style"
)
@Preview(
    showBackground = true,
    name = "BottomNavigation"
)
@Composable
fun BottomNavigationPreview() {
    val navController = rememberNavController()
    MultiplatformTheme {
        BottomNavigationBar(navController)
    }
}

/**
 * Usa este componente para encabezar la aplicación o bien el menú lateral
 */
@ShowkaseComposable(
    name = "TopAppBarMenuDrawer", group = "Navigation components",
    styleName = "Default Style"
)
@Preview(
    showBackground = true,
    name = "TopAppBarMenuDrawer"
)
@Composable
fun TopAppBarMenuDrawerPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val title = "Menu Drawer Preview"
    MultiplatformTheme {
        TopAppBarMenuDrawer(
            drawerState = drawerState,
            scope = scope,
            title = title
        )
    }
}

/**
 * En este componente el usuario ingresará su correo electrónico
 * Example usage:
 * ```
 * @Composable
 * fun MyEmailComposable() {
 *     MultiplatformTheme{
 *         val email = "example"
 *         EmailTextField(
 *             value = email,
 *             onValueChange = { email -> }
 *         )
 *     }
 * }
 * ```
 */
@ShowkaseComposable(name = "EmailTextField", group = "Login components")
@Preview(
    showBackground = true,
    name = "EmailTextField"
)
@Composable
fun EmailTextFieldPreview() {
    MultiplatformTheme{
        val email = "example"
        EmailTextField(
            value = email,
            onValueChange = { email -> }
        )
    }
}

/**
 * En este componente el usuario ingresará la contraseña
 * Example usage:
 * ```
 * @Composable
 * fun MyPasswordComposable() {
 *     MultiplatformTheme{
 *         val password = "example"
 *         PasswordTextField(
 *             value = password,
 *             onValueChange = { password -> }
 *         )
 *     }
 * }
 * ```
 */
@ShowkaseComposable(name = "PasswordTextField", group = "Login components")
@Preview(
    showBackground = true, name = "PasswordTextField"
)
@Composable
fun PasswordTextFieldPreview() {
    MultiplatformTheme{
        val password = "example"
        PasswordTextField(
            value = password,
            onValueChange = { password -> }
        )
    }
}

@ShowkaseComposable(name = "CustomButtonWithIcon", group = "Login components")
@Preview(
    showBackground = true,
    name = "CustomButtonWithIcon"
)
@Composable
fun CustomButtonWithIconPreview() {
    MultiplatformTheme{
        CustomButtonWithIcon(
            onClick = { },
            text = "Iniciar sesión",
            enabled = true,
            imageVector = Icons.Default.Person,
            contentDescription = "Login"
        )
    }
}

/**
 * Alerta que te avisa de que no está permitido las credenciales vacías
 */
@ShowkaseComposable(name = "InvalidDataAlertDialogLogin", group = "Dialog components")
@Preview(showBackground = true, name = "InvalidDataAlertDialogLogin")
@Composable
fun CustomDialogButtonPreview() {
    val showDialog = remember { mutableStateOf(false) }
    MultiplatformTheme{
        CustomDialogButton(
            onClick = { showDialog.value = true },
            text = "Ver InvalidDataAlertDialogLogin"
        )
        if (showDialog.value) {
            InvalidDataAlertDialogLogin(
                onDismissRequest = { showDialog.value = false }
            )
        }
    }
}

/**
 * Alerta que te avisa de que no estás introduciendo correctamente las credenciales.
 * Especifica de qué forma puedes hacerlo correctamente.
 */
@ShowkaseComposable(name = "ShowAlertDialogLogin", group = "Dialog components")
@Preview(showBackground = true, name = "ShowAlertDialogLogin")
@Composable
fun CustomDialogButtonPreview2() {
    val showDialog = remember { mutableStateOf(false) }
    MultiplatformTheme{
        CustomDialogButton(
            onClick = { showDialog.value = true },
            text = "Ver ShowAlertDialogLogin"
        )
        if (showDialog.value) {
            ShowAlertDialogLogin(
                title = "Credenciales vacías",
                texto = "Debes introducir un email y una contraseña",
                onDismissRequest = { showDialog.value = false }
            )
        }
    }
}
class FakeUpdateStorage : UpdateStorage {
    private var updateInfo: UpdateInfo? = null
    override fun saveUpdateAvailable(updateInfo: UpdateInfo) {
        this.updateInfo = updateInfo
    }
    override fun loadUpdateInfo(): UpdateInfo? {
        updateInfo = UpdateInfo(
            mustUpdate = true,
            currentVersion = "1.3",
            newVersion = "1.4"
        )
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
class FakeApiService : ApiService {
    override fun checkUpdateAvailable(): UpdateInfo {
        return UpdateInfo(
            mustUpdate = true,
            currentVersion = "1.3",
            newVersion = "1.4"
        )
    }
    override suspend fun requestLogin(id: String): String? { TODO() }
}
/**
 * Alerta que te avisa de que hay una nueva actualización de la aplicación
 */
@SuppressLint("StateFlowValueCalledInComposition")
@ShowkaseComposable(name = "UpdateAlertDialog", group = "Dialog components")
@Preview(showBackground = true, name = "UpdateAlertDialog")
@Composable
fun CustomDialogButtonPreview3() {
    var showDialog by remember { mutableStateOf(false) }
    MultiplatformTheme {
        CustomDialogButton(
            onClick = { showDialog = true },
            text = "Ver UpdateAlertDialog"
        )
        LaunchedEffect(showDialog){
            delay(3000)
            showDialog = false
        }
        if (showDialog) {
            MultiplatformTheme {
                val mockViewModel = UpdateVM(
                    updateStorage = FakeUpdateStorage(),
                    apiService = FakeApiService()
                )
                UpdateAlertDialog(viewModel = mockViewModel)
            }
        }
    }
}

//Issue: https://github.com/airbnb/Showkase/issues/235
/*@ShowkaseComposable(name = "InvalidDataAlertDialogLogin", group = "Dialog components")
@Preview(showBackground = true, name = "InvalidDataAlertDialogLogin")
@Composable
fun InvalidDataAlertDialogLoginPreview() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        InvalidDataAlertDialogLogin(
            onDismissRequest = { true }
        )
    }
}*/