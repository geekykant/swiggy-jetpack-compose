package com.example.swiggyapp.ui

import com.example.swiggyapp.R

sealed class ScreenItem(
    var route: String,
    var icon: Int,
    var icon_pressed: Int,
    var title: String
) {
    object Home : ScreenItem("Home", R.drawable.ic_location, R.drawable.ic_location, "Swiggy")
    object Search : ScreenItem("Search", R.drawable.ic_search, R.drawable.ic_search, "Search")
    object Cart : ScreenItem("Cart", R.drawable.ic_cart, R.drawable.ic_cart_filled, "Cart")
    object Account :
        ScreenItem("Account", R.drawable.ic_account, R.drawable.ic_account_filled, "Account")
}