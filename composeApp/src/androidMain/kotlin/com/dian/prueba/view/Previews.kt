package com.dian.prueba.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.dian.prueba.AppLogin
import com.dian.prueba.navigation.BottomNavigationBar
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
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

/**
 * AMAZON COMPONENTS
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
    SearchBarAmazon(
        query = "Mesa",
        onQueryChange = { newQuery -> },
        onSearch = { query -> },
        active = true,
        onActiveChange = { active -> }
    )
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
    ScrollRowAmazon()
}

@ShowkaseComposable(name = "AppLogin", group = "Amazon components")
@Preview(
    showBackground = true,
    name = "AppLogin"
)
@Composable
fun AmazonLoginPreview() {
    val navController = rememberNavController()
    LoginScreen(navController)
}

/**
 * NAVIGATION COMPONENTS
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
    BottomNavigationBar(navController)
}

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
    TopAppBarMenuDrawer(
        drawerState = drawerState,
        scope = scope,
        title = title
    )
}

/**
 * LOGIN COMPONENTS
 */
@ShowkaseComposable(name = "EmailTextField", group = "Login components")
@Preview(
    showBackground = true,
    name = "EmailTextField"
)
@Composable
fun EmailTextFieldPreview() {
    val email = "example"
    EmailTextField(
        value = email,
        onValueChange = { email -> }
    )
}

@ShowkaseComposable(name = "PasswordTextField", group = "Login components")
@Preview(
    showBackground = true, name = "PasswordTextField"
)
@Composable
fun PasswordTextFieldPreview() {
    val password = "example"
    PasswordTextField(
        value = password,
        onValueChange = { password -> }
    )
}

@ShowkaseComposable(name = "CustomButtonWithIcon", group = "Login components")
@Preview(
    showBackground = true,
    name = "CustomButtonWithIcon"
)
@Composable
fun CustomButtonWithIconPreview() {
    CustomButtonWithIcon(
        onClick = { },
        text = "Iniciar sesión",
        enabled = true,
        imageVector = Icons.Default.Person,
        contentDescription = "Login"
    )
}

/**
 * DIALOG COMPONENTS
 * Alerta que te avisa de que no está permitido las credenciales vacías
 */

@ShowkaseComposable(name = "InvalidDataAlertDialogLogin", group = "Dialog components")
@Preview(showBackground = true, name = "InvalidDataAlertDialogLogin")
@Composable
fun CustomDialogButtonPreview() {
    val showDialog = remember { mutableStateOf(false) }

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

/**
 * DIALOG COMPONENTS
 * Alerta que te avisa de que no estás introduciendo correctamente las credenciales.
 * Especifica de qué forma puedes hacerlo correctamente.
 */
@ShowkaseComposable(name = "ShowAlertDialogLogin", group = "Dialog components")
@Preview(showBackground = true, name = "ShowAlertDialogLogin")
@Composable
fun CustomDialogButtonPreview2() {
    val showDialog = remember { mutableStateOf(false) }

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

/**
 * DIALOG COMPONENTS
 * Alerta que te avisa de que hay una nueva actualización de la aplicación
 */
@ShowkaseComposable(name = "UpdateAlertDialog", group = "Dialog components")
@Preview(showBackground = true, name = "UpdateAlertDialog")
@Composable
fun CustomDialogButtonPreview3() {
    val showDialog = remember { mutableStateOf(false) }

    CustomDialogButton(
        onClick = { showDialog.value = true },
        text = "Ver UpdateAlertDialog"
    )
    if (showDialog.value) {
        UpdateAlertDialog()
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