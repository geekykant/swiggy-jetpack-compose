package com.example.swiggyapp.ui

import com.example.swiggyapp.R

sealed class BottomNavItem(
    val route: String,
    val icon: Int,
    val icon_pressed: Int,
    val title: String
) {
    object Home : BottomNavItem("Home", R.drawable.ic_location, R.drawable.ic_location, "Swiggy")
    object Search : BottomNavItem("Search", R.drawable.ic_search, R.drawable.ic_search, "Search")
    object Cart : BottomNavItem("Cart", R.drawable.ic_cart, R.drawable.ic_cart_filled, "Cart")
    object Account :
        BottomNavItem("Account", R.drawable.ic_account, R.drawable.ic_account_filled, "Account")
}