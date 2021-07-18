package com.example.swiggyapp.ui.home

import com.example.swiggyapp.data.QuickTile
import com.example.swiggyapp.data.Restaurant

class HomeViewModel () {
    data class HomeViewState(
        val quickTiles: List<QuickTile> = emptyList(),
        val refreshing: Boolean = false,
        val nearbyRestaurants: List<Restaurant> = emptyList(),
        val errorMessage: String? = null
    )
}