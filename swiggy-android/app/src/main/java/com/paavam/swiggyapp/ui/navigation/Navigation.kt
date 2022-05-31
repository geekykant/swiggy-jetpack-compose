package com.paavam.swiggyapp.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.paavam.swiggyapp.ui.RestaurantMain
import com.paavam.swiggyapp.ui.screens.*
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.viewmodel.SwiggyViewModel

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalFoundationApi
@Composable
fun MainNavigation(
    mainNavController: NavHostController,
    swiggyViewModel: SwiggyViewModel
) {
    AnimatedNavHost(mainNavController, startDestination = MainNavScreen.MainHome.route) {
        composable(MainNavScreen.MainHome.route) {
            val navController = rememberAnimatedNavController()

            Scaffold(
                topBar = { },
                bottomBar = { BottomNavigationBar(navController) }
            ) { outerPaddingValues ->
                // sets the NavHost and default start to [NavScreen.Home]
                NavigationSub(
                    navController,
                    outerPaddingValues,
                    swiggyViewModel,
                    mainNavController
                )
            }
        }
        composable(MainNavScreen.ShowAddresses.route) {
            /* Address Screen - Configure with local Room Database */
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    text = "Address Page",
                    style = Typography.h2,
                    modifier = Modifier.fillMaxWidth()
                )
                Divider(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .height(5.dp)
                )
                Text(
                    text = "Here you can add a page to add address book and store them locally in Room database. \n" +
                            "This will be sent to server corresponding to every order.",
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }

        composable(
            MainNavScreen.Restaurant.route + "/{restaurantId}",
//            arguments = listOf(navArgument("restaurantId") { type = NavType.LongType }),
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            }
        ) {
            it.arguments?.getString("restaurantId")?.toLong()?.let { restaurantId ->
                RestaurantMain(
                    restaurantId = restaurantId,
                    mainNavController = mainNavController
                )
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@ExperimentalFoundationApi
@Composable
fun NavigationSub(
    navController: NavHostController,
    outerPadding: PaddingValues,
    swiggyViewModel: SwiggyViewModel,
    mainNavController: NavHostController,
) {
    AnimatedNavHost(
        navController,
        startDestination = NavScreen.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable(NavScreen.Home.route) {
            MainContent(
                outerPadding,
                swiggyViewModel,
                mainNavController
            )
        }
        composable(NavScreen.Search.route) {
            SearchScreen(outerPadding)
        }
        composable(NavScreen.Cart.route) {
            CartScreen(navController, outerPadding)
        }
        composable(NavScreen.Account.route) {
            AccountScreen(outerPadding)
        }
    }
}
