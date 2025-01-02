package org.gcaguilar.kmmbeers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.gcaguilar.kmmbeers.presentation.Screen
import org.gcaguilar.kmmbeers.presentation.authentication.LoginScreen
import org.gcaguilar.kmmbeers.presentation.beer.BeerDetailScreen
import org.gcaguilar.kmmbeers.presentation.brewery.BreweryDetail
import org.gcaguilar.kmmbeers.presentation.search.SearchScreen
import org.gcaguilar.kmmbeers.presentation.splash.Splash
import org.gcaguilar.kmmbeers.presentation.ui.BackNavigationIcon
import org.gcaguilar.kmmbeers.presentation.ui.CustomAppBar


@Composable
fun App() {
    MainScreen()
}

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CustomAppBar(
                    navigationIcon = {
//                        if (navController.currentBackStack.value.last().destination.navigatorName != Screen.SEARCH.name) {
//                            BackNavigationIcon(onClickIconButton = { navController.popBackStack() })
//                        }
                    }
                )
            },
            content = { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Splash.route,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    composable(route = Screen.Splash.route) {
                        Splash(
                            navigate = {
                                navController.navigate(it)
                            }
                        )
                    }
                    composable(route = Screen.Authentitcation.route) {
                        LoginScreen(
                            navigate = {
                                navController.navigate(it)
                            }
                        )
                    }
                    composable(route = Screen.Search.route) {
                        SearchScreen(
                            navigate = {
                                navController.navigate(it)
                            }
                        )
                    }

                    composable(
                        route = Screen.BeerDetail.route, arguments = listOf(
                            navArgument("id") {
                                defaultValue = "0"
                                type = NavType.StringType
                            }
                        )
                    ) { navBackStackEntry ->
                        BeerDetailScreen(
                            bid = navBackStackEntry.arguments!!.getString("id", "0"),
                            navigate = {
                                navController.navigate(it)
                            }
                        )
                    }

                    composable(
                        route = Screen.BreweryDetail.route,
                        arguments = listOf(
                            navArgument("id") {
                                defaultValue = 0
                                type = NavType.IntType
                            })
                    ) { navBackStackEntry ->
                        BreweryDetail(
                            id = navBackStackEntry.arguments!!.getString("id", "0"),
                            navigate = {
                                navController.navigate(it)
                            }
                        )
                    }
                }
            }
        )
    }
}
