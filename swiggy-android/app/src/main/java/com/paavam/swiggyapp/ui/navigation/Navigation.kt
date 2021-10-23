package com.paavam.swiggyapp.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paavam.swiggyapp.ui.RestaurantMain
import com.paavam.swiggyapp.ui.screens.*
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.viewmodel.SwiggyViewModel

@ExperimentalFoundationApi
@Composable
fun MainNavigation(
    mainNavController: NavHostController,
    swiggyViewModel: SwiggyViewModel
) {
    NavHost(mainNavController, startDestination = MainNavScreen.MainHome.route) {
        composable(MainNavScreen.MainHome.route) {
            val navController = rememberNavController()

            Scaffold(
                topBar = { },
                bottomBar = { BottomNavigationBar(navController) }
            ) { outerPaddingValues ->
                // sets the NavHost and default start to [NavScreen.Home]
                Navigation(
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
//            arguments = listOf(navArgument("restaurantId") { type = NavType.LongType })
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


@ExperimentalFoundationApi
@Composable
fun Navigation(
    navController: NavHostController,
    outerPadding: PaddingValues,
    swiggyViewModel: SwiggyViewModel,
    mainNavController: NavHostController
) {
    NavHost(navController, startDestination = NavScreen.Home.route) {
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
