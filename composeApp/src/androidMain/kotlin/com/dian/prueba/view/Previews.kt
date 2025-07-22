package com.dian.prueba.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import com.dian.prueba.ui.screens.LoginScreen
import com.dian.prueba.ui.screens.ScrollRowAmazon
import com.dian.prueba.ui.screens.SearchBarAmazon

/**
 * AMAZON COMPONENTS
 */
@OptIn(ExperimentalMaterial3Api::class)
@ShowkaseComposable(name = "SearchBarAmazon", group = "Amazon components",
    styleName = "Default Style", heightDp = 80)
@Preview(
    showBackground = true, name = "SearchBarAmazon",
    heightDp = 80)
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
    styleName = "Default Style")
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
@ShowkaseComposable(name = "BottomNavigation", group = "Navigation components",
    styleName = "Default Style")
@Preview(
    showBackground = true,
    name = "BottomNavigation"
)
@Composable
fun BottomNavigationPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(navController)
}

@ShowkaseComposable(name = "TopAppBarMenuDrawer", group = "Navigation components",
    styleName = "Default Style")
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
 */
@ShowkaseComposable(name = "CustomDialogButton", group = "Dialog components")
@Preview(showBackground = true, name = "CustomDialogButton")
@Composable
fun CustomDialogButtonPreview() {
    CustomDialogButton(
        onClick = { },
        text = "Aceptar"
    )
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

/**
 * CUSTOM BUTTONS
 */