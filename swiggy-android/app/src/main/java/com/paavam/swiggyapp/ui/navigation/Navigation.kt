package com.paavam.swiggyapp.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.paavam.swiggyapp.ui.screens.AccountScreen
import com.paavam.swiggyapp.ui.screens.MainContent
import com.paavam.swiggyapp.ui.screens.SearchScreen
import com.paavam.swiggyapp.ui.screens.cart.CartScreen

@ExperimentalFoundationApi
@Composable
fun Navigation(
    navController: NavHostController,
    outerPaddingValues: PaddingValues
) {
    NavHost(navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            MainContent(outerPaddingValues)
        }
        composable(NavScreen.Search.route) {
            SearchScreen(outerPaddingValues)
        }
        composable(NavScreen.Cart.route) {
            CartScreen(navController, outerPaddingValues)
        }
        composable(NavScreen.Account.route) {
            AccountScreen(outerPaddingValues)
        }
    }
}
