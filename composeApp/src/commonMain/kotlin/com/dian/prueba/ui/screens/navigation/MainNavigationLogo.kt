package com.dian.prueba.ui.screens.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dian.prueba.liquidglass.components.navigation.ScreenBottom
import com.dian.prueba.ui.components.dialogs.ConfirmOrderDialog
import com.dian.prueba.viewModel.FeedVM
import com.dian.prueba.viewModel.NuFeedVM
import com.kyant.backdrop.backdrops.layerBackdrop
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.rememberHazeState
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun LogoNavigationScreen(
    feedVM: FeedVM,
    nuFeedVM: NuFeedVM,
    isAnimatedFinished: Boolean
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Rutas que muestran bottom navigation
    val mainRoutes = setOf(
        ScreenBottom.Newspaper.route,
        ScreenBottom.Closet.route,
        ScreenBottom.Profile.route,
    )
    val showBottomBar = currentRoute in mainRoutes
    val showTopAppBar = currentRoute in mainRoutes
    val showFabClippy = currentRoute in mainRoutes

    LaunchedEffect(currentRoute) {
        nuFeedVM.updateCurrentRoute(currentRoute)
        nuFeedVM.getCurrentRoute(currentRoute)
    }

    AppScaffold(
        navController = navController,
        showBottomBar = showBottomBar,
        showTopAppBar = showTopAppBar,
        showFabClippy = showFabClippy,
        content = { paddingValues, backdrop ->
            NavHost(
                navController = navController,
                startDestination = ScreenBottom.Closet.route,
                modifier = Modifier
                    .fillMaxSize()
                    //.hazeSource(hazeState)
                    .layerBackdrop(backdrop)
            ) {
                composable(ScreenBottom.Newspaper.route) {
                    NewsletterScreen(
                        paddingValues = paddingValues,
                    )
                }
                composable(ScreenBottom.Closet.route) {
                    ClosetScreen(
                        paddingValues = paddingValues,
                        isAnimatedFinished = isAnimatedFinished,
                        feedVM = feedVM,
                        nuFeedVM = nuFeedVM,
                        navigateTo = { navController.navigate(it) }
                    )
                }
                composable(ScreenBottom.Profile.route) {
                    ProfileScreen(
                        paddingValues = paddingValues,
                    )
                }
                composable("closet/confirmOrder") {
                    ConfirmOrderDialog()
                }
            }
        }
    )
}

/*@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun LogoNavigationScreen(
    feedVM: FeedVM,
    nuFeedVM: NuFeedVM,
    isAnimatedFinished: Boolean
) {
    val hazeState = rememberHazeState()
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    /*println("Current route: $currentRoute")
    nuFeedVM.getCurrentRoute(currentRoute)*/
    LaunchedEffect(currentRoute) {
        nuFeedVM.updateCurrentRoute(currentRoute)
        nuFeedVM.getCurrentRoute(currentRoute)
    }
    NavHost(
        navController = navController,
        startDestination = ScreenBottom.Closet.route,
        modifier = Modifier
            .fillMaxSize()
            .hazeSource(hazeState)
        //.layerBackdrop(backdrop)
    ) {
        composable(ScreenBottom.Newspaper.route) {
            NewsletterScreen(navController = navController)
        }

        composable(ScreenBottom.Closet.route) {
            ClosetScreen(
                navController = navController,
                isAnimatedFinished = isAnimatedFinished,
                feedVM = feedVM,
                nuFeedVM = nuFeedVM,
                navigateTo = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(ScreenBottom.Profile.route) {
            ProfileScreen(
                navController = navController
            )
        }

        composable("closet/confirmOrder") {
            ConfirmOrderDialog()
        }
    }
}*/
@Composable
fun ImageLogo(
    painter: Painter = painterResource(Res.drawable.logo),
    tint: Color,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painter,
        contentDescription = "Logo de Logo Circular Luxury Closet",
        modifier = modifier,
        tint = tint
    )
}