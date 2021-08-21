package com.paavam.swiggyapp.ui.navigation

import com.paavam.swiggyapp.R

sealed class NavScreen(
    val route: String,
    val icon: Int,
    val icon_pressed: Int,
    val title: String
) {
    object Home : NavScreen("home", R.drawable.ic_location, R.drawable.ic_location, "Swiggy")
    object Search : NavScreen("search", R.drawable.ic_search, R.drawable.ic_search, "Search")
    object Cart : NavScreen("cart", R.drawable.ic_cart, R.drawable.ic_cart_filled, "Cart")
    object Account :
        NavScreen("account", R.drawable.ic_account, R.drawable.ic_account_filled, "Account")
}