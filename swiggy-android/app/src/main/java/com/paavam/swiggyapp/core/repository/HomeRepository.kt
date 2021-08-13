package com.paavam.swiggyapp.core.repository

import com.paavam.swiggyapp.data.Cuisine
import com.paavam.swiggyapp.data.HelloBar
import com.paavam.swiggyapp.data.QuickTile
import com.paavam.swiggyapp.data.Restaurant
import javax.inject.Inject

/**
 * Repository for Home Screen
 */
class HomeRepository @Inject constructor(
    private val swiggyService: SwiggyService
) {
    suspend fun fetchTilesContent(): ResponseResult<List<QuickTile>> =
        swiggyService.fetchTilesContent()

    suspend fun fetchHelloBarContent(): ResponseResult<List<HelloBar>> =
        swiggyService.fetchHelloBarContent()

    suspend fun fetchRestaurantsList(): ResponseResult<List<Restaurant>> =
        swiggyService.fetchRestaurantsList()

    suspend fun fetchPopularCuisines(): ResponseResult<List<Cuisine>> =
        swiggyService.preparePopularCuisines()
}