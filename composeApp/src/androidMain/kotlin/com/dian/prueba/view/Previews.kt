package com.dian.prueba.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.dian.prueba.AppLogin
import com.dian.prueba.navigation.BottomNavigationBar
import com.dian.prueba.ui.screens.CartScreen
import com.dian.prueba.ui.screens.ScrollRowAmazon
import com.dian.prueba.ui.screens.SearchBarAmazon
import com.dian.prueba.ui.screens.SearchScreen

@ShowkaseComposable(
    name = "AppLogin",
    group = "Amazon"
)
@Preview(
    showBackground = true,
    name = "AppLogin"
)
@Composable
fun AppAndroidPreview() {
    AppLogin()
}

/**
 * ***************************************************************
 */

@ShowkaseComposable(
    name = "SearchBarAmazon",
    group = "Amazon",
    styleName = "Default Style"
)
@Preview(
    showBackground = true,
    name = "SearchBarAmazon"
)
@Composable
fun Amazon1() {
    SearchBarAmazon(
        query = "Mesa",
        onQueryChange = { newQuery -> },
        onSearch = { query -> },
        active = true,
        onActiveChange = { active -> }
    )
}
@ShowkaseComposable(
    name = "ScrollRowAmazon",
    group = "Amazon",
    styleName = "Default Style"
)
@Preview(
    showBackground = true,
    name = "ScrollRowAmazon"
)
@Composable
fun Amazon2() {
    ScrollRowAmazon()
}
/**
 * ***************************************************************
 */

@ShowkaseComposable(
    name = "CartScreen",
    group = "Screens",
    styleName = "Default Style"
)
@Preview(
    showBackground = true,
    name = "CartScreen"
)
@Composable
fun Screen1(){
    CartScreen()
}
@ShowkaseComposable(
    name = "SearchScreen",
    group = "Screens",
    styleName = "Default Style"
)
@Preview(
    showBackground = true,
    name = "SearchScreen"
)
@Composable
fun Screen2(){
    SearchScreen()
}

/**
 * ***************************************************************
 */
@ShowkaseComposable(
    name = "BottomNavigation",
    group = "Components",
    styleName = "Default Style"
)
@Preview(
    showBackground = true,
    name = "BottomNavigation"
)
@Composable
fun BottomNavigationPreview(){
    val navController = rememberNavController()
    BottomNavigationBar(navController)
}