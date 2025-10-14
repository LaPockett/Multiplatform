package com.dian.prueba

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import com.dian.prueba.utilities.TokenStorage
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.dian.prueba.HeaderManager.WebViewHeaderManager
import com.dian.prueba.ui.Theme.MultiplatformTheme
import com.dian.prueba.navigation.BottomNavigationBar
import com.dian.prueba.navigation.Screen
import com.dian.prueba.network.APIClient
import com.dian.prueba.ui.components.MenuDrawer
import com.dian.prueba.ui.screens.BrandScreen
import com.dian.prueba.ui.screens.WelcomeScreen
import com.dian.prueba.ui.screens.CartScreen
import com.dian.prueba.ui.screens.HomeScreen
import com.dian.prueba.ui.screens.LoginScreen
import com.dian.prueba.ui.screens.ProfileScreen
import com.dian.prueba.ui.screens.SearchScreen
import com.dian.prueba.utilities.Logger
import com.dian.prueba.ui.components.dialogs.UpdateAlertDialog
import com.dian.prueba.utilities.TokenStorageImpl
import com.dian.prueba.utilities.UpdateStorageImpl
import com.dian.prueba.viewModel.UpdateVM
import com.russhwolf.settings.*
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.amazon_logo
import multiplatform.composeapp.generated.resources.logo
import multiplatform.composeapp.generated.resources.matcha
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * NO SE USA
 */

@Composable
fun App() {
    MaterialTheme {
        val title by remember { mutableStateOf("Welcome to multiplatform") }
        var textFieldName by remember { mutableStateOf("") }
        val navController: NavHostController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "main"
        ) {
            composable(route = "main") {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(title)
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(
                        modifier = Modifier.padding(start = 20.dp, top = 10.dp)
                    ) {
                        TextField(
                            textFieldName,
                            onValueChange = { textFieldName = it },
                            label = { Text("Insert your name") }
                        )

                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                        onClick = {
                            if (textFieldName.isNotBlank()) {
                                navController.navigate("welcome/$textFieldName")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color(0xffeaf7f8),
                            containerColor = Color(0xff7265da)
                        )
                    ) {
                        Text("Next activity")
                    }
                }
            }
            composable(route = "welcome/{name}") { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: ""
                WelcomeScreen(name = name, navController = navController)
            }
            composable(route = "brand") {
                BrandScreen()
            }

        }

    }
}
@Composable
fun AppLogin() {
    /**
     * You are first checked to see if you are logged in, using tokens to redirect you
     * to the login screen or the menu drawer that contains the main application.
     */
    MultiplatformTheme {
        val logger = Logger("AppLogin")
        val settings = Settings()
        val tokenStorage: TokenStorage = TokenStorageImpl(settings)
        val navController = rememberNavController()

        if (settings.getStringOrNull("refresh_token") == null) {
            logger.debug(settings.getStringOrNull("refresh_token").toString())
            LoginScreen(navController)

        } else {
            logger.warn("El token no es nulo")
            logger.debug(settings.getStringOrNull("access_token").toString())
            logger.debug(settings.getStringOrNull("refresh_token").toString())
            tokenStorage.loadTokens()
            logger.debug(tokenStorage.loadTokens().toString())
            WebViewHeaderManager.updateRefreshToken(tokenStorage.loadTokens()!!.refreshToken!!)
            WebViewHeaderManager.updateAccessToken(tokenStorage.loadTokens()!!.accessToken)
            logger.debug(WebViewHeaderManager.getHeaders().toString())
            logger.warn("Ingresando a MenuDrawer")
            MenuDrawer(onLogout = {
                /**
                 * It’s used to remove destinations from the back stack when navigating to another destination.
                 * If the user is logged, the user will be redirected to the MenuDrawer screen and couldn’t go
                 * back to the login screen.
                 */
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            })
        }
    }
}

/**
 * APP AMAZON
 */

@Composable
fun AppNavigation(onLogout: () -> Unit) {
    val logger = Logger("AppNavigation")

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val noBarsRoutes = listOf(Screen.Login.route, Screen.Profile.route)
    val showBars = !noBarsRoutes.contains(currentRoute)

    val updateVM = remember {
        UpdateVM(
            updateStorage = UpdateStorageImpl(
                settings = Settings()
            ),
            apiService = APIClient(
                updateStorage = UpdateStorageImpl(
                    settings = Settings()
                )
            )
        )
    }

    LaunchedEffect(Unit) {
        logger.warn("Checking for updates...")
        updateVM.checkForUpdates()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            /**
             * Para que el bottomBar no salga en la pantalla de Profile (Account) y en LoginScreen,
             * pero sí en las demás pantallas
             */
            if (showBars) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        val graph =
            navController.createGraph(startDestination = Screen.Home.route) {
                composable(route = Screen.Cart.route) {
                    CartScreen()
                }
                composable(route = Screen.Explore.route) {
                    SearchScreen()
                }
                composable(route = Screen.Home.route) {
                    HomeScreen()
                }
                composable(route = Screen.Profile.route) {
                    ProfileScreen(navController, onLogout = onLogout)
                }
                composable(route = Screen.Login.route) {
                    LoginScreen(navController)
                }
            }
        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )
    }
    if (updateVM.showUpdateDialog.collectAsState().value) {
        UpdateAlertDialog(viewModel = updateVM)
    }
}


/*@Composable
@Preview
fun AppPrueba() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}

class Greeting {
    private val platform = getPlatformType()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}*/
/**
 * Creating a Logo with Frosted Glass Effect in Compose with Haze Library
 * https://proandroiddev.com/creating-a-logo-with-frosted-glass-effect-in-jetpack-compose-bad2453b8512
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Preview
@Composable
fun PruebaHaze() {
    val hazeState = rememberHazeState()

    MultiplatformTheme {
        Box {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 5.dp,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    items(50) { index ->
                        Image(
                            painter = painterResource(Res.drawable.matcha),
                            contentDescription = "description",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth().height(380.dp)
                        )
                    }
                },
                modifier = Modifier.fillMaxSize().hazeSource(hazeState)
            )
            TopAppBar(
                // Need to make app bar transparent to see the content behind
                colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
                modifier = Modifier
                    // We use hazeEffect on anything where we want the background
                    // blurred.
                    .hazeEffect(
                        state = hazeState,
                        style = HazeMaterials.ultraThin()
                    ){
                        progressive = HazeProgressive.verticalGradient(startIntensity = 0.5f, endIntensity = 0f)
                    }
                    .fillMaxWidth().padding(WindowInsets.safeDrawing.asPaddingValues()),
                title = {
                    Text("Matcha")
                },
                windowInsets = WindowInsets(
                    top = 0,
                    bottom = 0
                ),
                navigationIcon =
                    {
                        IconDefaultCustom(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.DarkGray
                        )
                    }
            )
        }
    }
}

@Composable
fun IconDefaultCustom(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    tint : Color = MaterialTheme.colorScheme.primary
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(32.dp)
            .padding(4.dp),
        tint = tint
    )
}




@Composable
fun PerplexityLogo(
    hazeStateLeft: HazeState,
    hazeStateRight: HazeState,
    modifier: Modifier
) {
    val containerColor = MaterialTheme.colorScheme.surface
    val lightAlpha = 0.3f
    val darkAlpha = 0.1f
    val hazeStyle = HazeStyle(
        backgroundColor = containerColor,
        tints = listOf(
            HazeTint(
                containerColor.copy(alpha = if (containerColor.luminance() >= 0.5) lightAlpha else darkAlpha),
            )
        ),
        blurRadius = 10.dp,
        noiseFactor = 0.3f,
        fallbackTint = HazeTint.Unspecified,
    )

    val infiniteTransition = rememberInfiniteTransition()
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(5_000),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            val pages = 8
            MutableList(pages) { index ->
                val rotationAngle = (animationProgress * 360 + (index * 360 / pages)) % 360
                if (rotationAngle > 0f && rotationAngle < 180f) {
                    RoundedBoxLeft(
                        rotationAngle - 90f,
                        hazeStateLeft,
                        hazeStyle,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.5f)
                            .align(Alignment.CenterStart)
                            .zIndex(rotationAngle)
                            .hazeSource(hazeStateLeft, rotationAngle + 1f)
                    )
                } else {
                    RoundedBoxRight(
                        rotationAngle + 90f,
                        hazeStateRight,
                        hazeStyle,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.5f)
                            .align(Alignment.CenterEnd)
                            .zIndex(360 - rotationAngle)
                            .hazeSource(hazeStateRight, 360 - rotationAngle + 1f)
                    )
                }
            }

        }
    }
}

@Composable
fun RoundedBoxRight(
    rotationAngle: Float,
    hazeState: HazeState,
    hazeStyle: HazeStyle,
    modifier: Modifier
) {
    Page(
        hazeState = hazeState,
        hazeStyle = hazeStyle,
        borderColor = Color.Transparent, // Here is the border
        modifier = modifier
            .graphicsLayer {
                rotationY = rotationAngle
                rotationX = -45f

                cameraDistance = 100f
                transformOrigin = TransformOrigin(
                    pivotFractionX = 0f,
                    pivotFractionY = 0.0f,
                )
            }
    )
}

@Composable
fun RoundedBoxLeft(
    rotationAngle: Float,
    hazeState: HazeState,
    hazeStyle: HazeStyle,
    modifier: Modifier
) {

    Page(
        hazeState = hazeState,
        hazeStyle = hazeStyle,
        borderColor = Color.Transparent, // Here is the border
        modifier = modifier
            .graphicsLayer {
                rotationY = rotationAngle
                rotationX = -45f

                cameraDistance = 100f
                transformOrigin = TransformOrigin(
                    pivotFractionX = 1f,
                    pivotFractionY = 0.0f,
                )
            }
    )
}

@Composable
fun Page(
    hazeState: HazeState,
    hazeStyle: HazeStyle,
    borderColor: Color,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .hazeEffect(hazeState, style = hazeStyle)
            .border(8.dp, borderColor)
    )
}


@Preview(showBackground = true, backgroundColor = 0xFF000023)
@Composable
fun PerplexityLogoPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val hazeStateLeft = remember { HazeState() }
        val hazeStateRight = remember { HazeState() }

        //background
        Box(
            modifier = Modifier
                .hazeSource(hazeStateRight, zIndex = 0f)
                .hazeSource(hazeStateLeft, zIndex = 0f)
                .clip(CircleShape)
                .paint(
                    painter = painterResource(resource = Res.drawable.logo),
                    contentScale = ContentScale.Crop
                )

        )
        PerplexityLogo(
            hazeStateLeft, hazeStateRight,
            modifier = Modifier.size(200.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000023)
@Composable
fun PreviewRoundedBox() {

    val containerColor = MaterialTheme.colorScheme.surface
    val lightAlpha = 0.3f
    val darkAlpha = 0.1f
    val hazeStyle = HazeStyle(
        backgroundColor = containerColor,
        tints = listOf(
            HazeTint(
                containerColor.copy(alpha = if (containerColor.luminance() >= 0.5) lightAlpha else darkAlpha),
            )
        ),
        blurRadius = 24.dp,
        noiseFactor = 0.1f,
        fallbackTint = HazeTint.Unspecified,
    )

    val hazeState = remember { HazeState() }
    RoundedBoxRight(
        0.5f, hazeState, hazeStyle, modifier = Modifier
    )
}




@Composable
fun PerplexityScreen() {
    val darkBackground = Color(0xFF121212)
    val grayText = Color(0xFF888888)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(resource = Res.drawable.amazon_logo),
                contentDescription = "Home",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )

            Text(
                text = "perplexity",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal
            )
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.White
            )
        }

        val infiniteTransition = rememberInfiniteTransition()
        val animationProgress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(8_000),
                repeatMode = RepeatMode.Restart
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                val hazeStateLeft = remember { HazeState() }
                val hazeStateRight = remember { HazeState() }
                Box(
                    modifier = Modifier
                        .offset(y = (-30).dp)
                        .size(300.dp)
                        .hazeSource(hazeStateRight, zIndex = 0f)
                        .hazeSource(hazeStateLeft, zIndex = 0f)
                        .graphicsLayer {
                            rotationZ = animationProgress * 1080
                        }
                        .clip(CircleShape)
                        .paint(
                            painter = painterResource(
                                resource = Res.drawable.logo
                            ),
                            contentScale = ContentScale.Crop
                        )

                )
                PerplexityLogo(
                    hazeStateLeft,
                    hazeStateRight,
                    modifier = Modifier.size(200.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Where",
                color = grayText,
                fontSize = 28.sp
            )
            Text(
                text = "knowledge",
                color = grayText,
                fontSize = 28.sp
            )
            Text(
                text = "begins",
                color = grayText,
                fontSize = 28.sp
            )
        }


        // Bottom content
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            // News ticker/carousel
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(3) { index ->
                    Card(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1E1E1E)
                        )
                    ) {
                        when (index) {
                            0 -> WeatherItem()
                            1 -> NewsItem()
                            2 -> AppUpdateItem()
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search input field
            TextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = { Text("Ask anything...") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF1E1E1E),
                    focusedContainerColor = Color(0xFF1E1E1E),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(36.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Voice input",
                        tint = Color.White
                    )
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = grayText
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = grayText
                )
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = grayText
                )
            }
        }
    }
}

@Composable
private fun WeatherItem() {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Weather",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text("1°C Sunny", color = Color.White)
    }
}

@Composable
private fun NewsItem() {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Musk-Led Group Bids for OpenAI",
            color = Color.White
        )
    }
}

@Composable
private fun AppUpdateItem() {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "App Update Available",
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerplexityScreenPreview() {
    PerplexityScreen()
}