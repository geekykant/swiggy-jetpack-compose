package com.paavam.swiggyapp.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            Text(
                text = "Hello!",
                style = Typography.h2,
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(
            MainNavScreen.Restaurant.route + "/{restaurantId}",
//            arguments = listOf(navArgument("restaurantId") { type = NavType.LongType }),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300, easing = LinearEasing))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300, easing = LinearEasing))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300, easing = LinearEasing))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300, easing = LinearEasing))
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
