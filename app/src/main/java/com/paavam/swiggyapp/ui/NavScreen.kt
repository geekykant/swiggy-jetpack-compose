package com.paavam.swiggyapp.ui

import com.paavam.swiggyapp.R

sealed class NavScreen(
    val route: String,
    val icon: Int,
    val icon_pressed: Int,
    val title: String
) {
    object Home : NavScreen("Home", R.drawable.ic_location, R.drawable.ic_location, "Swiggy")
    object Search : NavScreen("Search", R.drawable.ic_search, R.drawable.ic_search, "Search")
    object Cart : NavScreen("Cart", R.drawable.ic_cart, R.drawable.ic_cart_filled, "Cart")
    object Account :
        NavScreen("Account", R.drawable.ic_account, R.drawable.ic_account_filled, "Account")
}